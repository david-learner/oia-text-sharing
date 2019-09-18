package com.hardlearner.oia.repository;

import com.hardlearner.oia.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
