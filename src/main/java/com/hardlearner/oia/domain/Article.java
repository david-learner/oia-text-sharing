package com.hardlearner.oia.domain;

import java.util.List;

public class Article {
    ArticleInfo articleInfo;
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
}
