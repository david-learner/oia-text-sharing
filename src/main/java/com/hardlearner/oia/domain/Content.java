package com.hardlearner.oia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@NoArgsConstructor
@Getter
public class Content {
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ARTICLE_ID")
    private List<MainBlock> mainBlocks;
    // default value가 4인 이유는 처음 문서가 생성될 때 1개의 메인블록, 3개의 서브블록이 sequence를 사용하기 때문이다
    private int sequence = 5;

    public Content(List<MainBlock> mainBlocks) {
        this.mainBlocks = mainBlocks;
    }

    @JsonIgnore
    public Content getShareAllowed() {
        List<MainBlock> mainBlocks = new ArrayList<>();
        for (MainBlock mainBlock : this.mainBlocks) {
            mainBlocks.add(mainBlock.getShareAllowed());
        }
        return new Content(mainBlocks);
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        for (MainBlock mainBlock : mainBlocks) {
            sb.append(mainBlock.toString());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return print();
    }

    public void update(Content content) {

    }
}
