package com.example.bloggingapp.users;

import com.example.bloggingapp.users.dtos.CreateUserRequestDTO;
import com.example.bloggingapp.users.dtos.LoginUserDTO;
import com.example.bloggingapp.users.dtos.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    private List<UserResponseDTO> convertToUserResponses(List<UserEntity> userEntities) {
        List<UserResponseDTO> userResponses = new ArrayList<>();

        userEntities.forEach(userEntity -> userResponses.add(modelMapper.map(userEntity, UserResponseDTO.class)));

        return userResponses;
    }

    public UserResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO) {
        if (!createUserRequestDTO.getEmail().matches("\\w+@\\w+\\.\\w+"))
            throw new InvalidEmailException(createUserRequestDTO.getEmail());

        if (userRepository.existsByUsername(createUserRequestDTO.getUsername()))
            throw new UserExistException(createUserRequestDTO.getUsername());

        createUserRequestDTO.setPassword(passwordEncoder.encode(createUserRequestDTO.getPassword()));

        var userEntity = modelMapper.map(createUserRequestDTO, UserEntity.class);

        var savedUser = userRepository.save(userEntity);

        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    public UserResponseDTO getUserById(Integer userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        return modelMapper.map(user, UserResponseDTO.class);
    }

    public List<UserResponseDTO> getUsersByUsername(String username) {
        var userEntities = userRepository.findAllByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return convertToUserResponses(userEntities);
    }

    public List<UserResponseDTO> getAllUsers() {
        var users = userRepository.findAll();

        return convertToUserResponses(users);
    }

    public UserResponseDTO loginUser(LoginUserDTO loginUserDTO) {
        var userEntity = userRepository.findByUsername(loginUserDTO.getUsername());

        if (!passwordEncoder.matches(loginUserDTO.getPassword(), userEntity.getPassword()))
            throw new InvalidPasswordException();

        return modelMapper.map(userEntity, UserResponseDTO.class);
    }

    static class UserExistException extends IllegalArgumentException {
        public UserExistException(String username) {
            super("User with username: " + username  + " already exists");
        }
    }

    static class UserNotFoundException extends IllegalArgumentException {
        public UserNotFoundException(Integer userId) {
            super("User with id: " + userId + " does not exist");
        }

        public UserNotFoundException(String username) {
            super("User with username: " + username + " does not exist");
        }
    }

    static class InvalidEmailException extends IllegalArgumentException {
        public InvalidEmailException(String email) {
            super(email + " is invalid");
        }
    }

    static class InvalidPasswordException extends IllegalArgumentException {
        public InvalidPasswordException() {
            super("Invalid password");
        }
    }
}
