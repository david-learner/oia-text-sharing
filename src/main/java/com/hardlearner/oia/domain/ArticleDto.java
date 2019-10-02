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
    public Article toArticle(Member writer) {
        articleInfo.setWriter(writer);
        return new Article(id, articleInfo, content, writer);
    }
}
