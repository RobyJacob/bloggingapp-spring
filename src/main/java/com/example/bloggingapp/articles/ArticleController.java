package com.example.bloggingapp.articles;

import com.example.bloggingapp.users.dtos.UserPrincipalDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @GetMapping
    public String getAllArticles() {
        return "All articles";
    }

    @GetMapping("/private")
    public String getPrivateArticles(@AuthenticationPrincipal @NotNull UserPrincipalDTO user) {
        return "Private articles of " + user.getUserId();
    }
}
