package com.hardlearner.oia.domain;

import org.junit.*;

public class SubBlockTest {
    @Test
    public void create() {
        int page = 10;
        boolean canShare = true;
        String content = "본깨적은 본 것, 깨달은 것, 적용할 것을 말합니다.";
        SubBlock subBlock = new SubBlock(page, canShare, ContentCategory.OBSERVATION, content);
    }
}
