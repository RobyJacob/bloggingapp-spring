package com.example.bloggingapp.comments;

import com.example.bloggingapp.articles.ArticleEntity;
import com.example.bloggingapp.articles.ArticleService;
import com.example.bloggingapp.comments.dtos.CommentRequestDTO;
import com.example.bloggingapp.comments.dtos.CommentResponseDTO;
import com.example.bloggingapp.users.UserEntity;
import com.example.bloggingapp.users.UserService;
import com.example.bloggingapp.users.dtos.UserPrincipalDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRespository commentRespository;

    private final ArticleService articleService;

    private final UserService userService;

    private final ModelMapper modelMapper;

    public CommentService(CommentRespository commentRespository,
                          ArticleService articleService, UserService userService,
                          ModelMapper modelMapper) {
        this.commentRespository = commentRespository;
        this.articleService = articleService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public CommentResponseDTO createComment(String articleSlug, CommentRequestDTO commentDTO,
                                            UserPrincipalDTO userPrincipalDTO) {
        var article = modelMapper.map(articleService.getArticleBySlug(articleSlug),
                ArticleEntity.class);
        var commenter = modelMapper.map(userService.getUserByUsername(userPrincipalDTO.getUsername()),
                UserEntity.class);

        CommentEntity comment = new CommentEntity();
        comment.setTitle(commentDTO.getTitle());
        comment.setBody(commentDTO.getBody());
        comment.setCommenter(commenter);
        comment.setArticle(article);

        var savedComment = commentRespository.save(comment);

        return modelMapper.map(savedComment, CommentResponseDTO.class);
    }

    public List<CommentResponseDTO> getComments(String articleSlug) {
        var article = modelMapper.map(articleService.getArticleBySlug(articleSlug),
                ArticleEntity.class);

        var comments = commentRespository.findAllByArticle(article);

        List<CommentResponseDTO> commentResponses = new ArrayList<>();

        comments.forEach(comment -> commentResponses.add(modelMapper.map(comment,
                CommentResponseDTO.class)));

        return commentResponses;
    }
}
