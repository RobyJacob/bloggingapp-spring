package com.example.bloggingapp.articles.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateArticleRequestDTO {
    private String title;

    private String subTitle;

    private String body;
}
