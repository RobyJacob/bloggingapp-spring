package com.example.bloggingapp.comments;

import com.example.bloggingapp.articles.ArticleEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRespository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findAllByArticle(ArticleEntity article, Pageable pageable);
}
