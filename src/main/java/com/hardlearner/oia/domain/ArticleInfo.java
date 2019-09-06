package com.hardlearner.oia.domain;

import java.time.LocalDateTime;

public class ArticleInfo {
    Member writer;
    String title;
    LocalDateTime dateTime;

    public ArticleInfo(Member writer, String title, LocalDateTime dateTime) {
        this.writer = writer;
        this.title = title;
        this.dateTime = dateTime;
    }
}
