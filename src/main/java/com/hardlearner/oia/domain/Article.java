package com.hardlearner.oia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hardlearner.oia.dto.ArticleDto;
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
    private Long id;
    @Embedded
    private ArticleInfo articleInfo;
    @Embedded
    private Content content;
    private boolean deleted = false;

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

    @JsonIgnore
    public Article getShareAllowedArticle() {
        return new Article(id, articleInfo, content.getShareAllowedContent());
    }

    public Article getShareFullArticle(Member writer) throws AuthenticationException {
        if (!articleInfo.isSameWriter(writer)) {
            throw new AuthenticationException("Only this article's writer can access");
        }
        return this;
    }

    @JsonIgnore
    public String getTitle() {
        return articleInfo.getTitle();
    }

    @JsonIgnore
    public boolean isSameWriter(Member member) {
        return articleInfo.isSameWriter(member);
    }

    public Article update(ArticleDto articleDto) {
        articleInfo.update(articleDto.getArticleInfo());
        content.update(articleDto.getContent());
        return this;
    }

    public void delete() {
        deleted = true;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", articleInfo=" + articleInfo +
                ", content=" + content +
                ", deleted=" + deleted +
                '}';
    }
}
