package com.hardlearner.oia.domain;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PageInfo {
    private static final Logger log = LoggerFactory.getLogger(PageInfo.class);
    public static int PAGE_BLOCK_SIZE = 10;
    private int FIRST_PAGE = 1;

    // currentPage is zero-based
    private int currentPage;
    private int previousPage;
    private int nextPage;
    private List<Integer> currentPageBlock;

    public PageInfo(int total, int currentPage, int blockSize) {
        this.currentPage = currentPage;
        this.previousPage = getPreviousPage(currentPage);
        this.currentPageBlock = generateCurrentPageBlock(total, currentPage, blockSize);
        this.nextPage = getNextPage(currentPage, getCurrentBlockLastPage());
    }

    private List<Integer> generateCurrentPageBlock(int total, int currentPage, int blockSize) {
        List<Integer> currentPageBlock = new ArrayList<>();
        int currentBlockStart = getCurrentBlockStartPage(currentPage, blockSize);
        int nextBlockStart = currentBlockStart + blockSize;
        int currentBlockLast = nextBlockStart - 1;
        if (total < nextBlockStart) {
            currentBlockLast = total;
        }
        for (int index = 0; index <= currentBlockLast - currentBlockStart; index++) {
            int pageInBlock = index + currentBlockStart;
            currentPageBlock.add(pageInBlock);
        }
        return currentPageBlock;
    }

    private int getCurrentBlockStartPage(int currentPage, int blockSize) {
        int offsetCurrentPage = currentPage + 1;
        int remainder = (currentPage + 1) % blockSize;

        if (remainder > 0) {
            return ((offsetCurrentPage / blockSize) * blockSize) + 1;
        }
        if (remainder == 0) {
            return offsetCurrentPage - blockSize + 1;
        }

        throw new IllegalArgumentException("Please check page");
    }

    public int getCurrentBlockLastPage() {
        return currentPageBlock.get(currentPageBlock.size() - 1);
    }

    public int getPreviousPage(int currentPage) {
        if (currentPage > 0) {
            return currentPage - 1;
        }
        throw new IllegalArgumentException("Previous Page : Page should be bigger than 0");
    }

    public int getNextPage(int currentPage, int currentBlockLastPage) {
        if (currentPage < currentBlockLastPage) {
            return currentPage + 1;
        }
        throw new IllegalArgumentException("Next Page : Page can't be equal or bigger than last page");
    }

    public boolean isFirst() {
        return FIRST_PAGE == (currentPage + 1);
    }

    public boolean isLast() {
        return currentPage == getCurrentBlockLastPage();
    }
}
