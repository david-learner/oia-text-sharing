package com.hardlearner.oia.domain;

import java.time.LocalDateTime;
import java.util.Collection;

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

    @Override
    public String toString() {
        return "ArticleInfo{" +
                "writer=" + writer +
                ", title='" + title + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
