package com.hardlearner.oia.domain;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArticleTest {
    @Test
    public void delete() {
        assertFalse(DummyData.DUMMY_ARTICLE.isDeleted());
        DummyData.DUMMY_ARTICLE.delete();
        assertTrue(DummyData.DUMMY_ARTICLE.isDeleted());
    }
}
