package com.example.bloggingapp.comments.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDTO {
    private Integer id;

    private String title;

    private String body;
}
