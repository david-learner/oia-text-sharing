package com.hardlearner.oia.domain;

import java.util.ArrayList;
import java.util.List;

public class Content {
    List<MainBlock> mainBlocks = new ArrayList<>();

    public Content(List<MainBlock> mainBlocks) {
        this.mainBlocks = mainBlocks;
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        for (MainBlock mainBlock : mainBlocks) {
            sb.append(mainBlock.print());
        }
        return sb.toString();
    }
}
