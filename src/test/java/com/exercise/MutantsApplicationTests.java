package com.exercise;

import com.exercise.config.Profiles;
import com.exercise.domain.Dna;
import com.exercise.dto.MutantRequestDTO;
import com.exercise.processor.MutantProcessor;
import com.exercise.repository.DnaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles(Profiles.TEST)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MutantsApplicationTests {

	private final String [] MUTANT_DNA = { "ATGCGA" , "CAGTGC" , "TTATGT" , "AGAAGG" , "CCCCTA", "TCACTG"};
	private final String [] HUMAN_DNA = { "ACTGTG" , "ACTGCA" , "TGACAC" , "GTCAAC", "CTAATC", "TGTACT" };

	@Autowired
	private MutantProcessor mutantProcessor;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mvc;

    @Autowired
	private DnaRepository repository;

	@Test
	public void givenZeroRecords_getStats_shouldReturn200() throws Exception {
		mvc.perform(get("/mutant/stats"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.count_mutant_dna", equalTo(0)))
				.andExpect(jsonPath("$.count_human_dna", equalTo(0)));
	}

	@Test
	public void createMutant_shouldReturnOK() throws Exception {
		MutantRequestDTO mutantRequestDTO = new MutantRequestDTO(MUTANT_DNA,true);
		mvc.perform(post("/mutant/")
				.contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(mutantRequestDTO)))
				.andExpect(status().isOk());
	}

	@Test
	public void createHuman_shouldReturnForbidden() throws Exception {
		MutantRequestDTO mutantRequestDTO = new MutantRequestDTO(HUMAN_DNA,false);
		mvc.perform(post("/mutant/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mutantRequestDTO)))
				.andExpect(status().isForbidden());
	}

	@Test
	public void getStats_afterOneHumanAndOneMutant_shouldReturn1And1() throws Exception {
		MutantRequestDTO mutantDTO = new MutantRequestDTO(MUTANT_DNA, true);
		MutantRequestDTO humanDTO = new MutantRequestDTO(HUMAN_DNA, false);
		Dna humanDNA = new Dna(mutantProcessor.hash(humanDTO.getDna()),humanDTO.isMutant());
		Dna mutantDNA = new Dna(mutantProcessor.hash(mutantDTO.getDna()),mutantDTO.isMutant());
		repository.save(humanDNA);
		repository.save(mutantDNA);
		mvc.perform(get("/mutant/stats"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.count_mutant_dna", equalTo(1)))
				.andExpect(jsonPath("$.count_human_dna", equalTo(1)));
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void tryToAddAnExistentMutant(){
		MutantRequestDTO newMutantDTO = new MutantRequestDTO(MUTANT_DNA, true);
		String [] EXISTING_DNA = MUTANT_DNA;
		MutantRequestDTO existingMutantDTO = new MutantRequestDTO(EXISTING_DNA,true);
		repository.save(new Dna(mutantProcessor.hash(newMutantDTO.getDna()),true));
		repository.save(new Dna(mutantProcessor.hash(existingMutantDTO.getDna()),true));
	}
}
