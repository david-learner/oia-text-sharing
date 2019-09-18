package com.hardlearner.oia.domain;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
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
