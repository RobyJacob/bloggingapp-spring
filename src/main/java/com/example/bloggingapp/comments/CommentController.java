package com.example.bloggingapp.comments;

import com.example.bloggingapp.comments.dtos.CommentRequestDTO;
import com.example.bloggingapp.comments.dtos.CommentResponseDTO;
import com.example.bloggingapp.users.dtos.UserPrincipalDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/articles/{article_slug}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponseDTO> addComment(
            @PathVariable("article_slug") String articleSlug,
            @RequestBody CommentRequestDTO commentDTO,
            @AuthenticationPrincipal UserPrincipalDTO userPrincipalDTO) {
        var comment = commentService.createComment(articleSlug, commentDTO, userPrincipalDTO);

        return ResponseEntity.created(URI.create("/articles/" + articleSlug + "/comments/" + comment.getId()))
                .body(comment);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDTO>> getAllComments(
            @PathVariable("article_slug") String articleSlug) {
        return ResponseEntity.ok(commentService.getComments(articleSlug));
    }
}
