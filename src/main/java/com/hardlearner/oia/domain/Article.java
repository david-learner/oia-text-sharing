package com.hardlearner.oia.domain;

public class Article {
    ArticleInfo articleInfo;
    Content content;

    public Article(ArticleInfo articleInfo, Content content) {
        this.articleInfo = articleInfo;
        this.content = content;
    }
}
