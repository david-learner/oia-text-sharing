package com.hardlearner.oia.domain;

import javax.naming.AuthenticationException;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public static Article getDefaultArticle(Member member, LocalDateTime dateTime) {
        return new Article(new ArticleInfo(member, "제목없는 문서", dateTime), new Content(Arrays.asList(MainBlock.getDefaultMainBlock())));
    }

    public String getTitle() {
        return articleInfo.getTitle();
    }

    public Content getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", articleInfo=" + articleInfo.toString() +
                ", content=" + content.toString() +
                '}';
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

    public Long getId() {
        return id;
    }
}
