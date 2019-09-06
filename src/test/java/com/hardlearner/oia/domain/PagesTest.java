package com.hardlearner.oia.domain;

import org.h2.store.Page;
import org.junit.Test;

public class PagesTest {
    @Test
    public void create() {
        int start = 10;
        int end = 11;
        Pages pages = new Pages(start, end);
    }

    @Test(expected = IllegalArgumentException.class)
    public void page_is_lower_than_0() {
        int start = -1;
        int end = -2;
        Pages pages = new Pages(start, end);
    }

    @Test(expected = IllegalArgumentException.class)
    public void end_page_is_smaller_than_start() {
        int start = 5;
        int end = 4;
        Pages pages = new Pages(start, end);
    }
}
