package com.hardlearner.oia.domain;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SubBlockTest {

    @Test
    public void create() {
        Pages pages = new Pages(10, 10);
        boolean canShare = true;
        String content = "본깨적은 본 것, 깨달은 것, 적용할 것을 말합니다.";
        SubBlock subBlock = new SubBlock(pages, canShare, ContentCategory.OBSERVATION, content);
    }

    @Test
    public void print() {
        Pages pages = new Pages(11, 12);
        boolean canShare = true;
        String content = "본깨적은 본 것, 깨달은 것, 적용할 것을 말합니다.";
        SubBlock subBlock = new SubBlock(pages, canShare, ContentCategory.OBSERVATION, content);

        assertTrue(subBlock.print().contains(pages.toString()));
        assertTrue(subBlock.print().contains(content));
    }
}
