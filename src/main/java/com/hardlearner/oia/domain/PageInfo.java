package com.hardlearner.oia.domain;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class PageInfo {
    private static final Logger log =  LoggerFactory.getLogger(PageInfo.class);

    public static int PAGE_BLOCK_SIZE = 10;

    private int first = 1;
    private int last;
    // currentPage is zero-based
    private int prevPage;
    private int currentPage;
    private int nextPage;
    private List<Integer> currentBlock = new ArrayList<>();


    public PageInfo(int total, int currentPage, int blockSize) {
        this.last = total / blockSize;
        if (total % blockSize > 0) {
            this.last++;
        }
        this.currentPage = currentPage;
        if (currentPage != (first - 1)) {
            this.prevPage = currentPage - 1;
        }
        if (currentPage != (last - 1)) {
            nextPage = currentPage + 1;
        }

        int currentBlockStart = 0;
        int offsetCurrentPage = currentPage+1;
        if ((offsetCurrentPage % blockSize) == 0) {
            currentBlockStart = offsetCurrentPage - blockSize + 1;
        }
        if ((offsetCurrentPage % blockSize) > 0) {
            currentBlockStart = ((offsetCurrentPage / blockSize) * blockSize) + 1;
        }
        log.debug("currentPage : {}, offsetCurrentPage : {}, currentBlockStart : {}", currentPage, offsetCurrentPage, currentBlockStart);
        for (int index = 0; index < blockSize; index++) {
            int blockItem = index + currentBlockStart;
            if (blockItem > last) {
                break;
            }
            this.currentBlock.add(blockItem);
        }

        log.debug(Arrays.toString(currentBlock.toArray()));
    }

    public boolean isFirst() {
        return first == (currentPage + 1);
    }

    public boolean isLast() {
        return last == (currentPage + 1);
    }
}
