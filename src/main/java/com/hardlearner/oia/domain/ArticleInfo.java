package com.hardlearner.oia.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleInfo {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MEMBER_ID")
    Member writer;
    String title;
    @Column(columnDefinition = "TIMESTAMP")
    LocalDateTime dateTime;

    @Override
    public String toString() {
        return "ArticleInfo{" +
                "writer=" + writer.toString() +
                ", title='" + title + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }

    public boolean isSameWriter(Member writer) {
        return writer.equals(writer);
    }
}
