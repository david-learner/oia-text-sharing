package com.hardlearner.oia.domain;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MainBlockTest {
    @Test
    public void getShareAllowedMainBlock() {
        List<SubBlock> subBlocks = Arrays.asList(
                SubBlock.builder()
                        .pages(new Pages(1, 1))
                        .share(false)
                        .category(ContentCategory.OBSERVATION)
                        .content("코드로 익히는 객체지향 설계").build(),
                SubBlock.builder()
                        .pages(new Pages(2, 2))
                        .share(true)
                        .category(ContentCategory.OBSERVATION)
                        .content("역할, 책임, 협력 관점에서 본 객체지향").build());
        MainBlock mainBlock = MainBlock.builder()
                .pointers(new Pointers(null, null))
                .subBlocks(subBlocks).build();

        assertEquals(2, mainBlock.getSubBlocks().size());
        assertEquals(1, mainBlock.getShareAllowedMainBlock().getSubBlocks().size());
    }

    @Test
    public void getShareAllowedMainBlock_has_no_subBlocks() {
        List<SubBlock> subBlocks = Arrays.asList(
                SubBlock.builder()
                        .pages(new Pages(1, 1))
                        .share(false)
                        .category(ContentCategory.OBSERVATION)
                        .content("코드로 익히는 객체지향 설계").build(),
                SubBlock.builder()
                        .pages(new Pages(2, 2))
                        .share(false)
                        .category(ContentCategory.OBSERVATION)
                        .content("역할, 책임, 협력 관점에서 본 객체지향").build());
        MainBlock mainBlock = MainBlock.builder()
                .pointers(new Pointers(null, null))
                .subBlocks(subBlocks).build();

        assertEquals(0, mainBlock.getShareAllowedMainBlock().getSubBlocks().size());
    }
}
