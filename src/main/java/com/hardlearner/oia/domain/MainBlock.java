package com.hardlearner.oia.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public MainBlock getShareAllowed() {
        List<SubBlock> shareAllowedSubBlocks = this.subBlocks.stream().filter(subBlock -> subBlock.canShare).collect(Collectors.toList());
        return new MainBlock(this.id, shareAllowedSubBlocks);
    }

    public boolean isSame(MainBlock otherBlock) {
        return this.equals(otherBlock);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MainBlock mainBlock = (MainBlock) o;
        return Objects.equals(id, mainBlock.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
