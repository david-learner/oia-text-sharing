package com.hardlearner.oia.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainBlock {
    private final Long id;
    private final List<SubBlock> subBlocks;

    public MainBlock(Long id, List<SubBlock> subBlocks) {
        this.id = id;
        this.subBlocks = subBlocks;
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        for (SubBlock subBlock : subBlocks) {
            sb.append(subBlock.print());
        }
        return sb.toString();
    }

    public List<SubBlock> shareAllowed() {
        return this.subBlocks.stream().filter(subBlock -> subBlock.canShare).collect(Collectors.toList());
    }

//    public MainBlock shareAllowed() {
//        List<SubBlock> shareAllowedSubBlocks = this.subBlocks.stream().filter(subBlock -> subBlock.canShare).collect(Collectors.toList());
//        return new MainBlock(this.id, shareAllowedSubBlocks);
//    }
}
