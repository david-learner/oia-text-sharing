package com.hardlearner.oia.domain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class DummyData {
    public static Member dummyMember = new Member("dummydata@gmail.com", "dummypassword", "김더미");

    public static List<SubBlock> dummySubBlocks1 = Arrays.asList(
            SubBlock.builder()
                    .pages(new Pages(8, 8))
                    .canShare(true)
                    .category(ContentCategory.OBSERVATION)
                    .content("개발자는 구체적인 코드를 만지며 손을 더럽힐 때 가장 많은 것을 얻어가는 존재다")
                    .sequenceId(1)
                    .build(),
            SubBlock.builder()
                    .pages(new Pages(8, 8))
                    .canShare(false)
                    .category(ContentCategory.INTERPRETATION)
                    .content("개발자가 어떻게 일하는 사람인지 명확하게 보여주는 문장인 것 같다")
                    .sequenceId(2).build()
    );


    public static List<SubBlock> dummySubBlocks2 = Arrays.asList(
            SubBlock.builder()
                    .pages(new Pages(17, 17))
                    .canShare(true)
                    .category(ContentCategory.OBSERVATION)
                    .content("설계의 목표는 객체 사이의 결합도를 낮춰 변경이 용이한 설계를 만드는 것이어야 한다")
                    .sequenceId(1)
                    .build(),
            SubBlock.builder()
                    .pages(new Pages(17, 17))
                    .canShare(false)
                    .category(ContentCategory.INTERPRETATION)
                    .content("변경에 용이한 설계가 왜 중요한지는 토이프로젝트를 진행하며 조그마한 요구사항이 변경되었을 때 확실히 알 수 있었다")
                    .sequenceId(2)
                    .build(),
            SubBlock.builder()
                    .pages(new Pages(17, 17))
                    .canShare(true)
                    .category(ContentCategory.APPLICATION)
                    .content("변경에 용이한 설계를 위해 객체지향 5대 원칙을 최대한 적용하여 개발하도록 하자")
                    .sequenceId(3)
                    .build()
    );

    public static MainBlock dummyMainBlock1 = MainBlock.builder().sequenceId(1).subBlocks(dummySubBlocks1).build();
    public static List<MainBlock> dummyMainBlocks1 = Arrays.asList(MainBlock.builder().sequenceId(1).subBlocks(dummySubBlocks1).build());

    public static Content dummyContent = new Content(dummyMainBlocks1);

    public static ArticleInfo dummyArticleInfo = new ArticleInfo(dummyMember, "Dummy Title", LocalDateTime.now());
    public static Article dummyArticle = new Article(dummyArticleInfo, dummyContent);
}
