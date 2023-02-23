package com.example.bloggingapp.users;

import com.example.bloggingapp.commons.ErrorResponse;
import com.example.bloggingapp.users.dtos.CreateUserRequestDTO;
import com.example.bloggingapp.users.dtos.LoginUserDTO;
import com.example.bloggingapp.users.dtos.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        var savedUser = userService.createUser(createUserRequestDTO);

        return ResponseEntity.created(URI.create("/users/" + savedUser.getId())).body(savedUser);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("user_id") Integer userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUser(@RequestParam(name = "username") Optional<String> username) {
        return username.map(name -> ResponseEntity.ok(userService.getUsersByUsername(name)))
                .orElseGet(() -> ResponseEntity.ok(userService.getAllUsers()));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(@RequestBody LoginUserDTO loginUserDTO) {
        return ResponseEntity.accepted().body(userService.loginUser(loginUserDTO));
    }

    @ExceptionHandler({
            UserService.UserExistException.class,
            UserService.UserNotFoundException.class,
            UserService.InvalidEmailException.class,
            UserService.InvalidPasswordException.class,
            RuntimeException.class
    })
    public ResponseEntity<ErrorResponse> errorHandler(Exception ex) {
        if (ex instanceof UserService.UserExistException
                || ex instanceof UserService.UserNotFoundException
                || ex instanceof UserService.InvalidEmailException
                || ex instanceof UserService.InvalidPasswordException)
            return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));

        return ResponseEntity.internalServerError().body(new ErrorResponse(ex.getMessage()));
    }
}
