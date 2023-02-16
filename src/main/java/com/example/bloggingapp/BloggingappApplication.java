package com.example.bloggingapp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BloggingappApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloggingappApplication.class, args);
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
