package com.hardlearner.oia.domain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class DummyData {
    public static Member dummyMember = new Member("dummydata@gmail.com", "dummy7", "황더미");

    public static List<SubBlock> dummySubBlocks1 = Arrays.asList(
            SubBlock.builder()
                    .pages(new Pages(1, 2))
                    .canShare(true)
                    .category(ContentCategory.OBSERVATION)
                    .content("본깨적이란 본 것과 깨달은 것 그리고 적용할 것입니다")
                    .sequenceId(1)
                    .build(),
            SubBlock.builder()
                    .pages(new Pages(1, 2))
                    .canShare(false)
                    .category(ContentCategory.INTERPRETATION)
                    .content("사실 본깨적은 성경을 관찰하고 해석하여 삶에 적용하는 방법을 심플하게 만든 것이다")
                    .sequenceId(2).build()
    );

    public static List<SubBlock> dummySubBlocks2 = Arrays.asList(
            SubBlock.builder()
                    .pages(new Pages(10, 10))
                    .canShare(true)
                    .category(ContentCategory.OBSERVATION)
                    .content("본 것은 책에 쓰여진 내용을 저자의 관점으로 읽는 것입니다")
                    .sequenceId(1)
                    .build(),
            SubBlock.builder()
                    .pages(new Pages(10, 10))
                    .canShare(false)
                    .category(ContentCategory.INTERPRETATION)
                    .content("처음부터 저자의 관점으로 보기 힘들다. 책을 많이 읽다보면 자연스레 해당 컨텐스트에서 작가가 강조하고자 하는 것이 무엇인지 보인다.")
                    .sequenceId(2)
                    .build(),
            SubBlock.builder()
                    .pages(new Pages(10, 10))
                    .canShare(true)
                    .category(ContentCategory.APPLICATION)
                    .content("책 읽을 때 펜을 들고 해당 문장, 문단 또는 챕터에서 자신이 생각하기에 저자가 강조한 내용을 밑줄치기")
                    .sequenceId(3)
                    .build()
    );

    public static MainBlock dummyMainBlock1 = MainBlock.builder().sequenceId(1).subBlocks(dummySubBlocks1).build();
    public static List<MainBlock> dummyMainBlocks1 = Arrays.asList(MainBlock.builder().sequenceId(1).subBlocks(dummySubBlocks1).build());
    public static List<MainBlock> dummyMainBlocks2 = Arrays.asList(
            MainBlock.builder().sequenceId(1).subBlocks(dummySubBlocks1).build(),
            MainBlock.builder().sequenceId(2).subBlocks(dummySubBlocks2).build()
    );

    public static Content dummyContent = new Content(dummyMainBlocks1);

    public static ArticleInfo dummyArticleInfo = new ArticleInfo(dummyMember, "Dummy Title", LocalDateTime.now());
    public static Article dummyArticle = new Article(dummyArticleInfo, dummyContent);
}
