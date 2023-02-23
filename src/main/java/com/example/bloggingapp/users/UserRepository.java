package com.example.bloggingapp.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    boolean existsByUsername(String username);

    Optional<List<UserEntity>> findAllByUsername(String username);

    UserEntity findByUsername(String username);
}
