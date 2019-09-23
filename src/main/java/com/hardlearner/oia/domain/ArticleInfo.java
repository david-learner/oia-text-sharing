package com.hardlearner.oia.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// Lombok의 Builder는 기본적으로 멤버변수의 초기값을 지원하지 않는다.
// toBuilder 옵션을 true로 하면 멤버변수의 초기값을 지원한다
@Builder(toBuilder = true)
public class ArticleInfo {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MEMBER_ID")
    Member writer;
    String title;
    @Builder.Default
    @Column(columnDefinition = "TIMESTAMP")
    LocalDateTime dateTime = LocalDateTime.now();

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
