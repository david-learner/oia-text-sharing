package com.hardlearner.oia.repository;

import com.hardlearner.oia.domain.Article;
import com.hardlearner.oia.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    // Lazy
    List<Article> findAllByArticleInfo_Writer(Member member);
    List<Article> findAllByArticleInfo_Writer(Member member, Pageable pageable);
    // Eager
//    List<Article> findAllByArticleInfo_Writer_Id(Long id);

    @Transactional
    @Modifying
    @Query("delete from Article a where a.id in :ids")
    void deleteAllById(@Param("ids") List<Long> ids);
}
