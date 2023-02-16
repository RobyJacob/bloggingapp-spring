package com.example.bloggingapp.commons;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
@Getter
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt = LocalDate.now();
}
