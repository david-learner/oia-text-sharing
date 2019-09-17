package com.hardlearner.oia.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Content {
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
