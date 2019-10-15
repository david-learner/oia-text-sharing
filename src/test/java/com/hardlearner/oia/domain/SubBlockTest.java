package com.hardlearner.oia.domain;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SubBlockTest {
    @Test
    public void getDefaultSubBlocks() {
        List<SubBlock> subBlocks = SubBlock.getDefaultSubBlocks();
        for (int index = 0; index < subBlocks.size(); index++) {
            assertNotNull(subBlocks.get(index).getSequenceId());
        }

        // 기본 서브 블록들이 본, 깨, 적 순서로 카테고리를 가지고 있는지
        assertEquals(subBlocks.get(0).getContentCategory(), ContentCategory.OBSERVATION.getAlias());
        assertEquals(subBlocks.get(1).getContentCategory(), ContentCategory.INTERPRETATION.getAlias());
        assertEquals(subBlocks.get(2).getContentCategory(), ContentCategory.APPLICATION.getAlias());
    }
}
