package com.hardlearner.oia.repository;

import com.hardlearner.oia.domain.ContentCategory;
import com.hardlearner.oia.domain.Pages;
import com.hardlearner.oia.domain.SubBlock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SubBlockRepositoryTest {
    @Autowired
    private SubBlockRepository subBlockRepository;

    @Test
    public void save() {
        Pages pages = new Pages(1, 2);
        String text = "Spring 알아가는 재미가 쏠쏠하다";
        SubBlock subBlock = new SubBlock(pages, true, ContentCategory.OBSERVATION, text);
        SubBlock savedSubBlock = subBlockRepository.save(subBlock);
        assertTrue(savedSubBlock.print().contains(text));
        assertEquals(savedSubBlock.getContentCategory(), ContentCategory.OBSERVATION.getAlias());
    }
}
