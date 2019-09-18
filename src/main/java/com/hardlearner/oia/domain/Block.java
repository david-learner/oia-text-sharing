package com.hardlearner.oia.domain;

import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Block {
    @Id
    @GeneratedValue
    private Long id;
    private Integer sequenceId;
    @Embedded
    private Pointers pointers;

    public Block(Long id, Integer sequenceId, Pointers pointers) {
        this.id = id;
        this.sequenceId = sequenceId;
        this.pointers = pointers;
    }

    public Long getId() {
        return id;
    }
}
