package com.example.bloggingapp.users;

import com.example.bloggingapp.users.dtos.CreateUserRequestDTO;
import com.example.bloggingapp.users.dtos.CreateUserResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public CreateUserResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO) {
        // TODO encrupt password
        // TODO check if user exist
        // TODO validate email

        var userEntity = modelMapper.map(createUserRequestDTO, UserEntity.class);

        var savedUser = userRepository.save(userEntity);

        return modelMapper.map(savedUser, CreateUserResponseDTO.class);
    }
}
