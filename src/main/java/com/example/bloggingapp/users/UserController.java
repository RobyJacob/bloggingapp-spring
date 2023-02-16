package com.example.bloggingapp.users;

import com.example.bloggingapp.users.dtos.CreateUserRequestDTO;
import com.example.bloggingapp.users.dtos.CreateUserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        var savedUser = userService.createUser(createUserRequestDTO);

        return ResponseEntity.created(URI.create("/users/" + savedUser.getId())).body(savedUser);
    }
}
