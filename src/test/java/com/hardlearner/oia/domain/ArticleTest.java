package com.hardlearner.oia.domain;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArticleTest {
    @Test
    public void delete() {
        assertFalse(DummyData.dummyArticle.isDeleted());
        DummyData.dummyArticle.delete();
        assertTrue(DummyData.dummyArticle.isDeleted());
    }
}
