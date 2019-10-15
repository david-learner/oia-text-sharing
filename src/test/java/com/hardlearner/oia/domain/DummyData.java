package com.hardlearner.oia.domain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class DummyData {
    public static Member dummyMember = new Member("dummydata@gmail.com", "dummypassword", "김더미");

    public static List<SubBlock> dummySubBlocks = Arrays.asList(
            SubBlock.builder()
                    .pages(new Pages(8, 8))
                    .share(true)
                    .category(ContentCategory.OBSERVATION)
                    .content("개발자는 구체적인 코드를 만지며 손을 더럽힐 때 가장 많은 것을 얻어가는 존재다")
                    .sequenceId(1)
                    .build(),
            SubBlock.builder()
                    .pages(new Pages(8, 8))
                    .share(false)
                    .category(ContentCategory.INTERPRETATION)
                    .content("개발자가 어떻게 일하는 사람인지 명확하게 보여주는 문장인 것 같다")
                    .sequenceId(2).build()
    );

    public static MainBlock dummyMainBlock = MainBlock.builder().sequenceId(1).subBlocks(dummySubBlocks).build();
    public static List<MainBlock> dummyMainBlocks = Arrays.asList(dummyMainBlock);

    public static Content dummyContent = new Content(dummyMainBlocks);

    public static ArticleInfo dummyArticleInfo = new ArticleInfo(dummyMember, "Dummy Title", LocalDateTime.now());
    public static Article dummyArticle = new Article(dummyArticleInfo, dummyContent);
}
