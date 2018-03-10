package com.exercise.domain;

import javax.persistence.*;

/**
 * Created by vgerb on 3/9/18.
 */
@Entity(name = "dna")
@Table(name = "dna")
public class Dna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hash", unique = true)
    private Long hash;

    @Column(name="mutant")
    private Boolean mutant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHash() {
        return hash;
    }

    public void setHash(Long hash) {
        this.hash = hash;
    }

    public Boolean getMutant() {
        return mutant;
    }

    public void setMutant(Boolean mutant) {
        this.mutant = mutant;
    }

    public Dna(Long hash, Boolean mutant) {
        this.hash = hash;
        this.mutant = mutant;
    }
}
