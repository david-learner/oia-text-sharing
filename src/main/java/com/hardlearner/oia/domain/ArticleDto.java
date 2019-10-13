package com.hardlearner.oia.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
