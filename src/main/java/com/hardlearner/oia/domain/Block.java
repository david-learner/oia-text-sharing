package com.hardlearner.oia.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;

@MappedSuperclass
@Getter
@AllArgsConstructor
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer sequenceId;
    @Embedded
    private Pointers pointers;

    // 이 생성자는 Lombok의 @AllArgsConstructor 에 대응된다
//    public Block(Long id, Integer sequenceId, Pointers pointers) {
//        this.id = id;
//        this.sequenceId = sequenceId;
//        this.pointers = pointers;
//    }
}
