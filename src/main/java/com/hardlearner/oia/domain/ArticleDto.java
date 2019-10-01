package com.hardlearner.oia.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ArticleDto {
    Long id;
    ArticleInfo articleInfo;
    Content content;
//    private String title;
    // FE에서 save할 때 어떤 것들을 받아들이고 저장할지
    public Article toArticle(Member writer) {
        articleInfo.setWriter(writer);
        return new Article(id, articleInfo, content, writer);
    }
}
