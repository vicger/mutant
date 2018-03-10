package com.exercise.service;

import com.exercise.domain.Dna;
import com.exercise.dto.MutantRequestDTO;
import com.exercise.dto.MutantStatsDTO;
import com.exercise.processor.MutantProcessor;
import com.exercise.repository.DnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by vgerb on 3/9/18.
 */
@Service
public class MutantService {

    private DnaRepository dnaRepository;
    private MutantProcessor mutantProcessor;

    @Autowired
    public MutantService(DnaRepository dnaRepository,
                         MutantProcessor mutantProcessor){
        this.dnaRepository = dnaRepository;
        this.mutantProcessor = mutantProcessor;
    }

    public boolean isMutant(String [] dna){
        return mutantProcessor.isMutant(dna);
    }

    @Async
    @Transactional
    public void persistDNA(MutantRequestDTO mutantRequestDTO) {
        Dna dna = new Dna(mutantProcessor.hash(mutantRequestDTO.getDna()), mutantRequestDTO.isMutant());
        dnaRepository.save(dna);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public MutantStatsDTO getStats(){
        return this.dnaRepository.countMutants();
    }

}
