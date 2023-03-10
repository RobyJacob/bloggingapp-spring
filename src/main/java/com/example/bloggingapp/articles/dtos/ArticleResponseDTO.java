package com.example.bloggingapp.articles.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ArticleResponseDTO {
    private Integer id;

    private LocalDate createdAt;

    private String title;

    private String subTitle;

    private String body;

    private String slug;

    private AuthorResponse author;
}
