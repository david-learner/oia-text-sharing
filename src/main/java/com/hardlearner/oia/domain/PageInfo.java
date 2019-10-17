package com.hardlearner.oia.domain;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PageInfo {
    private static final Logger log = LoggerFactory.getLogger(PageInfo.class);
    public static final int BLOCK_SIZE = 10;
    public static final int PAGE_SIZE = 10;
    private int FIRST_PAGE = 1;

    private int articlesTotalCount;
    private Integer currentPage;
    private Integer previousPage;
    private Integer nextPage;
    private List<Integer> block;

    public PageInfo(int articlesTotalCount, int currentPage) {
        this.articlesTotalCount = articlesTotalCount;
        this.currentPage = currentPage;
        if (!isFirst()) {
            this.previousPage = getPreviousPage();
        }
        if (!isLast()) {
            this.nextPage = getNextPage();
        }
        this.block = getBlock();
    }

    public List<Integer> getBlock() {
        List<Integer> block = new ArrayList<>();
        int blockStartPage = getBlockStartPage();
        int blockLastPage = getBlockLastPage();
        for (int index = blockStartPage; index <= blockLastPage; index++) {
            block.add(index);
        }
        return block;
    }

    public Integer getBlockStartPage() {
        if ((currentPage % PAGE_SIZE == 0)) {
            return (((currentPage / PAGE_SIZE) - 1) * PAGE_SIZE) + 1;
        }
        return ((currentPage / PAGE_SIZE) * PAGE_SIZE) + 1;
    }

    public Integer getBlockLastPage() {
        int blockLastPage = getBlockStartPage() + (PAGE_SIZE - 1);
        int lastPage = getTotalLastPage();
        if (lastPage < blockLastPage) {
            return lastPage;
        }
        return blockLastPage;
    }

    public Integer getTotalLastPage() {
        int lastPage = articlesTotalCount / PAGE_SIZE;
        if (articlesTotalCount % PAGE_SIZE > 0) {
            lastPage = (articlesTotalCount / PAGE_SIZE) + 1;
        }
        return lastPage;
    }

    public Integer getPreviousPage() {
        if (currentPage > 1) {
            return currentPage - 1;
        }
        throw new IllegalArgumentException("Page should be over 1");
    }

    public Integer getNextPage() {
        if (!isLast()) {
            return currentPage + 1;
        }
        throw new IllegalArgumentException("Page should be under last page");
    }

    public boolean isFirst() {
        return currentPage == 1;
    }

    public boolean isLast() {
        if (getTotalLastPage() == 0) {
            return true;
        }
        return getTotalLastPage() == currentPage;
    }
}
