package com.hardlearner.oia.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ArticleDto {
    private String title;
    private LocalDateTime dateTime;
}
