package com.example.bloggingapp.articles;

import com.example.bloggingapp.commons.BaseEntity;
import com.example.bloggingapp.users.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity(name = "articles")
@Getter
@Setter
public class ArticleEntity  extends BaseEntity {
    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "slug", nullable = false)
    private String slig;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "body", nullable = false, length = 1000)
    private String body;

    @ManyToOne
    private UserEntity author;

    @ManyToMany
    private List<UserEntity> likedBy;
}
