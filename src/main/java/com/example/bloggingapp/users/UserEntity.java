package com.example.bloggingapp.users;

import com.example.bloggingapp.articles.ArticleEntity;
import com.example.bloggingapp.commons.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "users")
@Getter
@Setter
public class UserEntity extends BaseEntity {
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "auth_token", nullable = false)
    private String authToken;

    @Column(name = "bio", nullable = false)
    private String bio;

    @Column(name = "image", nullable = false)
    private String image;

    @ManyToMany(mappedBy = "likedBy")
    private List<ArticleEntity> likedArticles;

    @ManyToMany
    @JoinTable(name = "followers", joinColumns = @JoinColumn(name = "follower_id"))
    private List<UserEntity> following;

    @ManyToMany(mappedBy = "following")
    private List<UserEntity> follower;
}
