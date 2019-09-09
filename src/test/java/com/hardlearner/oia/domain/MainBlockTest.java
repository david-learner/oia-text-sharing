package com.hardlearner.oia.domain;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class MainBlockTest {
    MainBlock mainBlock1;
    MainBlock mainBlock2;
    List<SubBlock> subBlocks1;
    List<SubBlock> subBlocks2;

    @Before
    public void setUp() {
        // 메인블록 테스트에서는 경계값 테스트를 포함하여 다양한 데이터의 조합으로 테스트가 필요하기에 별도로 만들어야 했다

        subBlocks1 = Arrays.asList(
                new SubBlock(new Pages(1, 2), true, ContentCategory.OBSERVATION, "본깨적이란 본 것과 깨달은 것 그리고 적용할 것입니다"),
                new SubBlock(new Pages(1, 2), true, ContentCategory.INTERPRETATION, "사실 본깨적은 성경을 보고 해석하고 삶에 적용하는 방법을 심플하게 만든 것이다"),
                new SubBlock(new Pages(1, 2), true, ContentCategory.APPLICATION, "책 읽을 때 펜을 들고 해당 문장, 문단 또는 챕터에서 자신이 생각하기에 저자가 강조한 내용을 밑줄치기")
        );
        subBlocks2 = Arrays.asList(
                new SubBlock(new Pages(10, 10), true, ContentCategory.OBSERVATION, "본 것은 책에 쓰여진 내용을 저자의 관점으로 읽는 것입니다"),
                new SubBlock(new Pages(10, 10), false, ContentCategory.INTERPRETATION, "처음부터 저자의 관점으로 보기 힘들다. 책을 많이 읽다보면 자연스레 해당 컨텐스트에서 작가가 강조하고자 하는 것이 무엇인지 보인다."),
                new SubBlock(new Pages(10, 10), true, ContentCategory.APPLICATION, "책 읽을 때 펜을 들고 해당 문장, 문단 또는 챕터에서 자신이 생각하기에 저자가 강조한 내용을 밑줄치기")
        );

        mainBlock1 = new MainBlock(1L, subBlocks1);
        mainBlock2 = new MainBlock(2L, subBlocks2);
    }

    @Test
    public void shareAllowed() {
        List<SubBlock> shareAllowedSubBlocks = mainBlock1.shareAllowed();
        int firstIndex = 0;
        int endIndex = shareAllowedSubBlocks.size() - 1;

        assertTrue(shareAllowedSubBlocks.get(firstIndex).canShare);
        assertTrue(shareAllowedSubBlocks.get(endIndex).canShare);
    }

    @Test
    public void shareNotAllowed() {

    }
}
