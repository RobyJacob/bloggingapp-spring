package com.example.bloggingapp.articles;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {
    @Query(value = "select * from articles",
        countQuery = "select count(1) from articles ", nativeQuery = true)
    List<ArticleEntity> findAllArticles(Pageable page);

    List<ArticleEntity> findAllBySlug(String slug);
}
