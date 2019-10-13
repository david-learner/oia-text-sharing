package com.hardlearner.oia.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class PageInfoTest {
    private PageInfo pageInfo;

    @Before
    public void setUp() {
        int total = 32;
        int currentPage = 15;
        int blockSize = 10;
        pageInfo = new PageInfo(total, currentPage, blockSize);
    }

    @Test
    public void getPreviousPage() {
        assertEquals(14, pageInfo.getPreviousPage(15));
    }

    @Test
    public void getCurrentBlockStartPage_꽉_채워진_블록() {
        int total = 32;
        int currentPage = 15;
        int blockSize = 10;
        PageInfo pageInfo = new PageInfo(total, currentPage, blockSize);
        List<Integer> currentPageBlock = pageInfo.getCurrentPageBlock();
        List<Integer> pageBlock = new ArrayList<>(Arrays.asList(11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
        assertEquals(pageBlock, currentPageBlock);
    }

    @Test
    public void getCurrentBlockStartPage_꽉_채워지지_않은_블록() {
        int total = 18;
        int currentPage = 15;
        int blockSize = 10;
        PageInfo pageInfo = new PageInfo(total, currentPage, blockSize);
        List<Integer> currentPageBlock = pageInfo.getCurrentPageBlock();
        List<Integer> pageBlock = new ArrayList<>(Arrays.asList(11, 12, 13, 14, 15, 16, 17, 18));
        assertEquals(pageBlock, currentPageBlock);
    }
}
