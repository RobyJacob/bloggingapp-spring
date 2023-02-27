package com.example.bloggingapp.users;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    boolean existsByUsername(String username);

    UserEntity findByUsername(String username);

    @Query(value = "select * from users",
            countQuery = "select count(1) from users", nativeQuery = true)
    List<UserEntity> findAllUsers(Pageable page);
}
