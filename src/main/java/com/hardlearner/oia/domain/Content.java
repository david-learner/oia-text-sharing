package com.hardlearner.oia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    // 양방향 관계가 아니라면 JPA에서는 foreign key를 타고 가서 삭제할 수 없다
    // 따라서 @OnDelete를 사용해서 DB에서 제공하는 cascade를 사용해야 하는 것
    @OneToMany(cascade = CascadeType.ALL)
    // DB의 foreign key에 붙일 수 있는 ON DELETE CASCADE 옵션
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ARTICLE_ID")
    private List<MainBlock> mainBlocks;
    // default value가 5인 이유는 처음 문서가 생성될 때 1개의 메인블록, 3개의 서브블록이 sequence를 사용하기 때문이다
    private int sequence = 5;

    public Content(List<MainBlock> mainBlocks) {
        this.mainBlocks = mainBlocks;
    }

    @JsonIgnore
    public Content getShareAllowedContent() {
        List<MainBlock> mainBlocks = new ArrayList<>();
        for (MainBlock mainBlock : this.mainBlocks) {
            MainBlock shareAllowedMainBlock = mainBlock.getShareAllowedMainBlock();
            if (shareAllowedMainBlock.isEmpty()) {
                continue;
            }
            mainBlocks.add(shareAllowedMainBlock);
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
        // mainBlock을 교체하는 것과 변경된 사항만 찾아서 list를 update해주는 것이 JPA 성능 관점에서 차이가 있는가?
        // JPA에서 동일한 데이터로 보는 기준은 무엇인가? pk?
        this.mainBlocks = content.getMainBlocks();
    }
}
