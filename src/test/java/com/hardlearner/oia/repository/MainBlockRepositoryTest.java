package com.hardlearner.oia.repository;

import com.hardlearner.oia.domain.MainBlock;
import com.hardlearner.oia.domain.Pointers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MainBlockRepositoryTest {
    @Autowired
    MainBlockRepository mainBlockRepository;

    @Test
    public void save() {
        MainBlock mainBlock = new MainBlock(null, 1, new Pointers(null, null), null, null);
        MainBlock savedMainBlock = mainBlockRepository.save(mainBlock);
        assertNotNull(savedMainBlock.getId());
    }
}
