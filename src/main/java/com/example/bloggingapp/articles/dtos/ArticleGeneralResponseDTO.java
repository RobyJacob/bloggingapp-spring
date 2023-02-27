package com.example.bloggingapp.articles.dtos;

import com.example.bloggingapp.users.dtos.AuthorResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleGeneralResponseDTO {
    private String title;

    private String subTitle;

    private AuthorResponse author;

    private String body;
}
