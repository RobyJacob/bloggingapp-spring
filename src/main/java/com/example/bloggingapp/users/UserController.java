package com.example.bloggingapp.users;

import com.example.bloggingapp.commons.ErrorResponse;
import com.example.bloggingapp.users.dtos.CreateUserRequestDTO;
import com.example.bloggingapp.users.dtos.LoginUserDTO;
import com.example.bloggingapp.users.dtos.UserResponseDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers(@RequestParam(value = "page", defaultValue = "0")
                                                              Integer page,
                                                          @RequestParam(value = "size", defaultValue = "5")
                                                          Integer size) {
        Pageable pageable = PageRequest.of(page, Math.min(size, 5));

        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDTO> getUsersByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(@RequestBody LoginUserDTO loginUserDTO,
                                                     @RequestParam(name = "auth_type", defaultValue = "jwt")
                                                     String authType) {
        AuthType auth = AuthType.JWT;

        if (authType.equals("auth_token")) auth = AuthType.AUTH_TOKEN;

        return ResponseEntity.accepted().body(userService.loginUser(loginUserDTO, auth));
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
