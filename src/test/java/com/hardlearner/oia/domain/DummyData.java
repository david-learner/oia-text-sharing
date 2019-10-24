package com.hardlearner.oia.domain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class DummyData {
    public static Member DUMMY_MEMBER = new Member("dummydata@gmail.com", "dummypassword", "김더미");

    public static String DUMMY_ARTICLE_SHARE_LINK_KEY = "dbbd78587f3031ea2fec3a9d0a40042b4e7bd4cdaddfc693c35f6211af66a25c";

    public static List<SubBlock> DUMMY_SUB_BLOCKS = Arrays.asList(
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

    public static MainBlock DUMMY_MAIN_BLOCK = MainBlock.builder().sequenceId(1).subBlocks(DUMMY_SUB_BLOCKS).build();
    public static List<MainBlock> DUMMY_MAIN_BLOCKS = Arrays.asList(DUMMY_MAIN_BLOCK);

    public static Content DUMMY_CONTENT = new Content(DUMMY_MAIN_BLOCKS);

    public static ArticleInfo DUMMY_ARTICLE_INFO = new ArticleInfo(DUMMY_MEMBER, "Dummy Title", LocalDateTime.now());
    public static Article DUMMY_ARTICLE = new Article(DUMMY_ARTICLE_INFO, DUMMY_CONTENT);
}
