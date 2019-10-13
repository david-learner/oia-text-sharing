package com.hardlearner.oia.dto;

import com.hardlearner.oia.domain.Article;
import com.hardlearner.oia.domain.ArticleInfo;
import com.hardlearner.oia.domain.Content;
import com.hardlearner.oia.domain.Member;
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
