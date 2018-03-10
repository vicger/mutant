package com.exercise.repository;

import com.exercise.domain.Dna;
import com.exercise.dto.MutantStatsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by vgerb on 3/9/18.
 */
public interface DnaRepository extends JpaRepository<Dna, Long> {

    @Query(value = "select new com.exercise.dto.MutantStatsDTO(sum(case when d.mutant = true then 1 else 0 end)," +
                        " sum(case when d.mutant = false then 1 else 0 end)) from dna d")
    MutantStatsDTO countMutants();
}
