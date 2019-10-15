package com.hardlearner.oia.domain;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ContentTest {
    @Test
    public void getShareAllowedContent_has_mainBlock() {
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

        Content content = new Content(Arrays.asList(mainBlock));
        Content shareAllowedContent = content.getShareAllowedContent();
        assertEquals(1, shareAllowedContent.getMainBlocks().size());
    }

    @Test
    public void getShareAllowedContent_has_no_mainBlock() {
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

        Content content = new Content(Arrays.asList(mainBlock));
        Content shareAllowedContent = content.getShareAllowedContent();
        assertEquals(0, shareAllowedContent.getMainBlocks().size());
    }
}
