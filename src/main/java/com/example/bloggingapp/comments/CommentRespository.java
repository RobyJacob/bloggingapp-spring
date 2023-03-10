package com.example.bloggingapp.comments;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRespository extends JpaRepository<CommentEntity, Integer> {
}
