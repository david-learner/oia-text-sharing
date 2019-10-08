package com.hardlearner.oia.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PageInfo {
    private int first = 1;
    private int last;
    private int currentPage;
    private List<Integer> currentBlock = new ArrayList<>();
    private int blockSize;

    public PageInfo(int total, int currentPage, int blockSize) {
        this.last = total / blockSize;
        if (total % blockSize > 0) {
            this.last++;
        }
        this.currentPage = currentPage;
        int currentBlockStart = (currentPage / blockSize) + 1;
        this.blockSize = blockSize;
        for (int index = 0; index < blockSize; index++) {
            int blockItem = index + currentBlockStart;
            if (blockItem > last) {
                break;
            }
            this.currentBlock.add(blockItem);
        }
    }
}
