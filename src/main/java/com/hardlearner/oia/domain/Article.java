package com.hardlearner.oia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.naming.AuthenticationException;
import javax.persistence.*;
import java.util.Arrays;

@Entity
@Getter
@NoArgsConstructor
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
        this(null, articleInfo, content);
    }

    public Article(Long id, ArticleInfo articleInfo, Content content) {
        this.id = id;
        this.articleInfo = articleInfo;
        this.content = content;
    }

    public Article(Long id, ArticleInfo articleInfo, Content content, Member writer) {
        this.id = id;
        this.articleInfo = articleInfo;
        if (!this.articleInfo.isSameWriter(writer)) {
            throw new IllegalArgumentException("Doesn't match writer");
        }
        this.content = content;
    }

    public static Article getDefaultArticle(Member member, MainBlock mainBlock) {
        return new Article(ArticleInfo.builder().writer(member).title("제목없는 문서").build()
                , new Content(Arrays.asList(mainBlock)));
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

    @JsonIgnore
    public String getTitle() {
        return articleInfo.getTitle();
    }

    public Article update(ArticleDto articleDto) {
        articleInfo.update(articleDto.getArticleInfo());
        content.update(articleDto.getContent());
        return this;
    }
}
