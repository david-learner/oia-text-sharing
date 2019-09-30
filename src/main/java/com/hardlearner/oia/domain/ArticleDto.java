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
    // FE에서 save할 때 어떤 것들을 받아들이고 저장할지
}
