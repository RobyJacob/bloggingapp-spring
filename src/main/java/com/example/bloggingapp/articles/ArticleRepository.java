package com.example.bloggingapp.articles;

import com.example.bloggingapp.users.UserEntity;
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

    List<ArticleEntity> findAllByAuthor(Pageable page, UserEntity author);

    ArticleEntity findBySlugAndAuthor(String articleSlug, UserEntity author);

    ArticleEntity findBySlug(String slug);
}
