package com.hardlearner.oia.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Entity;


@Embeddable
@Getter
@NoArgsConstructor
public class Pointers {
    private Integer prevPointer;
    private Integer nextPointer;

    public Pointers(Integer prevPointer, Integer nextPointer) {
        this.prevPointer = prevPointer;
        this.nextPointer = nextPointer;
    }
}
