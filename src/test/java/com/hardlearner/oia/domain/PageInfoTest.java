package com.hardlearner.oia.domain;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PageInfoTest {
    @Test
    public void isLast() {
        PageInfo pageInfo = new PageInfo(135, 14);
        assertTrue(pageInfo.isLast());
    }

    @Test
    public void isLast_페이지에_맞게_딱_떨어지는_아티클_개수() {
        PageInfo pageInfo = new PageInfo(100, 10);
        assertTrue(pageInfo.isLast());
    }

    @Test
    public void getPreviousPage() {
        PageInfo pageInfo = new PageInfo(135, 14);
        assertEquals(new Integer(13), pageInfo.getPreviousPage());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getPreviousPage_is_first_page() {
        PageInfo pageInfo = new PageInfo(135, 1);
        pageInfo.getPreviousPage();
    }

    @Test
    public void getNextPage() {
        PageInfo pageInfo = new PageInfo(135, 1);
        assertEquals(new Integer(2), pageInfo.getNextPage());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNextPage_is_last_page() {
        PageInfo pageInfo = new PageInfo(135, 14);
        pageInfo.getNextPage();
    }

    @Test
    public void getBlock() {
        PageInfo pageInfo = new PageInfo(135, 5);
        List<Integer> resultBlock = pageInfo.getBlock();
        List<Integer> expectedBlock = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        assertEquals(expectedBlock, resultBlock);
    }

    @Test
    public void getBlockStartPage() {
        PageInfo pageInfo = new PageInfo(135, 13);
        assertEquals(new Integer(11), pageInfo.getBlockStartPage());
    }

    @Test
    public void getBlockLastPage() {
        PageInfo pageInfo = new PageInfo(135, 5);
        assertEquals(new Integer(10), pageInfo.getBlockLastPage());
    }

    @Test
    public void getBlockLastPage_last_page_and_current_page_are_same() {
        PageInfo pageInfo = new PageInfo(135, 10);
        assertEquals(new Integer(10), pageInfo.getBlockLastPage());
    }

    @Test
    public void getBlockLastPage_total_last_page() {
        PageInfo pageInfo = new PageInfo(135, 11);
        assertEquals(new Integer(14), pageInfo.getBlockLastPage());
    }
}
