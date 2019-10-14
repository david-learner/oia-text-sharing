package com.hardlearner.oia.domain;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ArticleTest {
    @Test
    public void delete() {
        assertFalse(DummyData.dummyArticle.isDeleted());
        DummyData.dummyArticle.delete();
        assertTrue(DummyData.dummyArticle.isDeleted());
    }
}
