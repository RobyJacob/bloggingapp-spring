package com.example.bloggingapp.comments;

import com.example.bloggingapp.articles.ArticleEntity;
import com.example.bloggingapp.commons.BaseEntity;
import com.example.bloggingapp.users.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "comments")
@Getter
@Setter
public class CommentEntity extends BaseEntity {
    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "body", nullable = false, length = 1000)
    private String body;

    @ManyToOne
    private UserEntity commenter;

    @ManyToOne
    private ArticleEntity article;
}
