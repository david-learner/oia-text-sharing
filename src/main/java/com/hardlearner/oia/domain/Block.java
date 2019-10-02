package com.hardlearner.oia.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@MappedSuperclass
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer sequenceId;
    @Embedded
    private Pointers pointers;
}
