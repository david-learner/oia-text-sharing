package com.hardlearner.oia.domain;

import javax.naming.AuthenticationException;
import javax.persistence.*;

@Entity
public class Article {
    @Id @GeneratedValue
    @Column(name = "ARTICLE_ID")
    Long id;
    @Embedded
    ArticleInfo articleInfo;
    @Embedded
    Content content;

    public Article(ArticleInfo articleInfo, Content content) {
        this.articleInfo = articleInfo;
        this.content = content;
    }

    public String getTitle() {
        return articleInfo.getTitle();
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append(articleInfo.toString());
        sb.append(content.print());
        return sb.toString();
    }

    public Article getShareAllowed(Member writer) throws AuthenticationException {
        if (!articleInfo.isSameWriter(writer)) {
            throw new AuthenticationException("Only this article's writer can access");
        }
        return new Article(articleInfo, content.getShareAllowed());
    }

    public Article getShareFull(Member writer) throws AuthenticationException {
        if (!articleInfo.isSameWriter(writer)) {
            throw new AuthenticationException("Only this article's writer can access");
        }
        return this;
    }
}
