package com.hardlearner.oia.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Content {
    // 일대다 단방향 맵핑하게 되면 실제 테이블에서는 MainBlock에 FK(참조값)가 추가된다
    // 즉, 객체에서 참조하는 값(List<MainBlock>)은 Content 객체가 들고 있는데 DB에서는 MainBlock 테이블에 Content-ID가 FK로 추가되는 것이다
    // 헷갈리기 때문에 다대일을 양방향으로 맵핑하는 방식을 사용하여 객체와 테이블이 참조값을 동일하게 추가하는 방향으로 하는 게 실수를 줄이는 방법이 된다
    // 다만, OOP적으로는 약간의 손해를 본다. MainBlock내에 Content가 굳이 필요없는데 추가되기 때문이다. Trade Off
    @OneToMany(mappedBy = "article")
    private List<MainBlock> mainBlocks;

    public Content(List<MainBlock> mainBlocks) {
        this.mainBlocks = mainBlocks;
    }

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
            sb.append(mainBlock.print());
        }
        return sb.toString();
    }
}
