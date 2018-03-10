package com.exercise.dto;

/**
 * Created by vgerb on 3/9/18.
 */
public class MutantRequestDTO {

    private String [] dna;
    private boolean isMutant;

    public MutantRequestDTO(String[] dna, boolean isMutant) {
        this.dna = dna;
        this.isMutant = isMutant;
    }

    private MutantRequestDTO() {
    }

    public boolean isMutant() {
        return isMutant;
    }

    public void setMutant(boolean mutant) {
        isMutant = mutant;
    }

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }
}
