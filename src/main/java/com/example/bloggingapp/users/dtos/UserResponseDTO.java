package com.example.bloggingapp.users.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private Integer id;

    private String username;

    private String email;

    private String bio;

    private String image;
}
