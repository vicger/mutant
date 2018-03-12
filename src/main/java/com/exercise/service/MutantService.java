package com.exercise.service;

import com.exercise.cache.MutantCache;
import com.exercise.domain.Dna;
import com.exercise.dto.MutantRequestDTO;
import com.exercise.dto.MutantStatsDTO;
import com.exercise.processor.MutantProcessor;
import com.exercise.repository.DnaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private MutantCache mutantCache;

    private static final Logger logger = LoggerFactory.getLogger(MutantService.class);

    @Autowired
    public MutantService(DnaRepository dnaRepository,
                         MutantProcessor mutantProcessor,
                         MutantCache mutantCache) {
        this.dnaRepository = dnaRepository;
        this.mutantProcessor = mutantProcessor;
        this.mutantCache = mutantCache;
    }

    public boolean isMutant(String[] dna) {
        Long hash = mutantProcessor.hash(dna);
        if (mutantCache.contains(hash)) {
            logger.debug("Cache hit - No need to reprocess DNA");
            return mutantCache.get(hash);
        } else {
            logger.debug("Processing DNA " + dna);
            boolean isMutant = mutantProcessor.isMutant(dna);
            logger.debug("Is it mutant? " + isMutant);
            mutantCache.put(hash, isMutant);
            return isMutant;
        }
    }

    @Async
    @Transactional
    public void process(MutantRequestDTO mutantRequestDTO) {
        Dna dna = new Dna(mutantProcessor.hash(mutantRequestDTO.getDna()), mutantRequestDTO.isMutant());
        logger.debug("Attempting to save DNA into DB");
        dnaRepository.save(dna);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public MutantStatsDTO getStats(){
        return this.dnaRepository.countMutants();
    }
}
