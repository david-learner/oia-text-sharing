package com.hardlearner.oia.domain;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class PointersTest {
    @Test
    public void create() {
        Integer prevPointer = null;
        Integer nextPointer = null;
        Pointers pointers = new Pointers(prevPointer, nextPointer);
        assertNotNull(pointers);
    }
}
