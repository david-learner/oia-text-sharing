package com.hardlearner.oia.repository;

import com.hardlearner.oia.domain.SubBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubBlockRepository extends JpaRepository<SubBlock, Long> {
    
}
