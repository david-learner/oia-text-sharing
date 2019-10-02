package com.hardlearner.oia.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
@Getter
public class Pointers {
    private Integer prevPointer;
    private Integer nextPointer;

    public Pointers() {
    }

    public Pointers(Integer prevPointer, Integer nextPointer) {
        this.prevPointer = prevPointer;
        this.nextPointer = nextPointer;
    }
}
