package com.example.bloggingapp.articles;

import com.example.bloggingapp.articles.dtos.ArticleGeneralResponseDTO;
import com.example.bloggingapp.articles.dtos.ArticleResponseDTO;
import com.example.bloggingapp.articles.dtos.CreateArticleRequestDTO;
import com.example.bloggingapp.articles.dtos.UpdateArticleRequestDTO;
import com.example.bloggingapp.commons.ErrorResponse;
import com.example.bloggingapp.users.dtos.UserPrincipalDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<List<ArticleGeneralResponseDTO>> getAllArticles(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "author", required = false) String author) {
        Pageable pageable = PageRequest.of(page, Math.min(size, 10));

        return ResponseEntity.ok(articleService.getAllArticles(pageable, author));
    }

    @PostMapping
    public ResponseEntity<ArticleResponseDTO> createArticle(
            @RequestBody CreateArticleRequestDTO createArticleRequestDTO,
            @AuthenticationPrincipal UserPrincipalDTO principalDTO) {
        var articleResponse = articleService.createArticle(createArticleRequestDTO, principalDTO);

        return ResponseEntity.created(URI.create("/articles/" + articleResponse.getSlug()))
                .body(articleResponse);
    }

    @GetMapping("/{article_slug}")
    public ResponseEntity<ArticleResponseDTO> getArticleBySlug(
            @PathVariable("article_slug") String articleSlug) {
        return ResponseEntity.ok(articleService.getArticleBySlug(articleSlug));
    }

    @PatchMapping("/{article_slug}")
    public ResponseEntity<ArticleResponseDTO> updateArticle(
            @PathVariable("article_slug") String articleSlug,
            @AuthenticationPrincipal UserPrincipalDTO principalDTO,
            @RequestBody UpdateArticleRequestDTO updateArticleRequestDTO) {
        var updatedArticle = articleService.updateArticle(articleSlug, updateArticleRequestDTO,
                principalDTO);

        return ResponseEntity.accepted().body(updatedArticle);
    }

    @ExceptionHandler(ArticleService.ArticleNotFoundException.class)
    public ResponseEntity<ErrorResponse> errorHandler(RuntimeException ex) {
        if (ex instanceof ArticleService.ArticleNotFoundException) {
            return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
        }

        return ResponseEntity.internalServerError().body(new ErrorResponse(ex.getMessage()));
    }
}
