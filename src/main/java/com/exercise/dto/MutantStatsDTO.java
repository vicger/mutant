package com.exercise.dto;

/**
 * Created by vgerb on 3/9/18.
 */
public class MutantStatsDTO {

    private long count_mutant_dna;
    private long count_human_dna;
    private Float ratio;

    public MutantStatsDTO(Long count_mutant_dna, Long count_human_dna) {
        this.count_mutant_dna = count_mutant_dna != null ? count_mutant_dna.longValue() : 0L;
        this.count_human_dna =  count_human_dna != null ? count_human_dna.longValue() : 0L;

        if(this.count_human_dna == 0L){
            this.ratio = 0F;
        } else {
            this.ratio = (float) this.count_mutant_dna / this.count_human_dna;
        }
    }

    private MutantStatsDTO() {}
    public long getCount_mutant_dna() {
        return count_mutant_dna;
    }

    public float getRatio() {
        return ratio;
    }

    public long getCount_human_dna() {
        return count_human_dna;
    }
}
