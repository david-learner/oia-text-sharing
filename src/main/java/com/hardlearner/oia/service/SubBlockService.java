package com.hardlearner.oia.service;

import com.hardlearner.oia.domain.MainBlock;
import com.hardlearner.oia.domain.SubBlock;
import com.hardlearner.oia.repository.MainBlockRepository;
import com.hardlearner.oia.repository.SubBlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubBlockService {
    @Autowired
    SubBlockRepository subBlockRepository;

    public SubBlock save(SubBlock subBlock) {
        return subBlockRepository.save(subBlock);
    }

    public List<SubBlock> saveAll(List<SubBlock> subBlocks) {
        return subBlockRepository.saveAll(subBlocks);
    }
}
