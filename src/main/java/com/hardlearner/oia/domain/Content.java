package com.hardlearner.oia.domain;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Content {
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "ARTICLE_ID")
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
            sb.append(mainBlock.toString());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return print();
    }
}
