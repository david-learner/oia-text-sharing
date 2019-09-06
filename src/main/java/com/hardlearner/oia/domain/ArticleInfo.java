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

    public Member getWriter() {
        return writer;
    }

    public void setWriter(Member writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
