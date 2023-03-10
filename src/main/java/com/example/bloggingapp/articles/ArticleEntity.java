package com.example.bloggingapp.articles;

import com.example.bloggingapp.commons.BaseEntity;
import com.example.bloggingapp.users.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "articles")
@Getter
@Setter
public class ArticleEntity  extends BaseEntity {
    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "body", nullable = false, length = 1000)
    private String body;

    @ManyToOne
    @JoinColumn(nullable = false)
    private UserEntity author;

    @ManyToMany
    private List<UserEntity> likedBy;
}
