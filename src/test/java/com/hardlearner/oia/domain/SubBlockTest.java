package com.hardlearner.oia.domain;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

        assertTrue(subBlock.toString().contains(pages.toString()));
        assertTrue(subBlock.toString().contains(content));
    }

    @Test
    public void getDefaultSubBlocks() {
        List<SubBlock> subBlocks = SubBlock.getDefaultSubBlocks();
        // 기본 서브 블록들이 0, 1, 2 순서로 sequence id를 가지고 있는지
        for (int index = 0; index < subBlocks.size(); index++) {
            assertEquals(subBlocks.get(index).getSequenceId(), new Integer(index));
        }

        // 기본 서브 블록들이 본, 깨, 적 순서로 카테고리를 가지고 있는지
        assertEquals(subBlocks.get(0).getContentCategory(), ContentCategory.OBSERVATION.getAlias());
        assertEquals(subBlocks.get(1).getContentCategory(), ContentCategory.INTERPRETATION.getAlias());
        assertEquals(subBlocks.get(2).getContentCategory(), ContentCategory.APPLICATION.getAlias());
    }
}
