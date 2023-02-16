package com.example.bloggingapp.users.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestDTO {
    private String username;

    private String email;

    private String password;
}
