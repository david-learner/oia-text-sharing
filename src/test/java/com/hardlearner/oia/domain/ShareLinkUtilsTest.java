package com.hardlearner.oia.domain;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class ShareLinkUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(ShareLinkUtilsTest.class);

    @Test
    public void generate() {
        String shareLinkWithKey = ShareLinkUtils.generate("/api/articles/1/share");
        assertTrue(shareLinkWithKey.startsWith("/articles/1/share?key="));
    }
}
