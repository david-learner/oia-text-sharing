package com.hardlearner.oia.domain;

import javax.persistence.*;

@MappedSuperclass
public class Block {
    @Id @GeneratedValue
    private Long id;
    @Embedded
    private Pointers pointers;

    public Block(Long id, Pointers pointers) {
        this.id = id;
        this.pointers = pointers;
    }

    public Long getId() {
        return id;
    }
}
