package com.example.bloggingapp.articles;

import com.example.bloggingapp.articles.dtos.ArticleGeneralResponseDTO;
import com.example.bloggingapp.articles.dtos.ArticleResponseDTO;
import com.example.bloggingapp.articles.dtos.CreateArticleRequestDTO;
import com.example.bloggingapp.users.UserEntity;
import com.example.bloggingapp.users.UserService;
import com.example.bloggingapp.users.dtos.UserPrincipalDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    private final ModelMapper modelMapper;

    private final UserService userService;

    public ArticleService(ArticleRepository articleRepository,
                          ModelMapper modelMapper,
                          UserService userService) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public List<ArticleGeneralResponseDTO> getAllArticles(Pageable page) {
        List<ArticleEntity> articles = articleRepository.findAllArticles(page);

        List<ArticleGeneralResponseDTO> articleResponses = new ArrayList<>();

        articles.forEach(article -> articleResponses.add(modelMapper.map(article,
                ArticleGeneralResponseDTO.class)));

        return articleResponses;
    }

    public ArticleResponseDTO createArticle(CreateArticleRequestDTO createArticleRequestDTO,
                                            UserPrincipalDTO principalDTO) {
        String articleSlug = createArticleRequestDTO.getTitle().replaceAll("\\s", "") + '-' +
                createArticleRequestDTO.getSubTitle().replaceAll("\\s", "");

        var author = modelMapper.map(userService.getUserByUsername(principalDTO.getUsername()),
                UserEntity.class);

        var articleEntity = modelMapper.map(createArticleRequestDTO, ArticleEntity.class);
        articleEntity.setSlug(articleSlug);
        articleEntity.setAuthor(author);

        var savedArticle = articleRepository.save(articleEntity);

        return modelMapper.map(savedArticle, ArticleResponseDTO.class);
    }

    public List<ArticleGeneralResponseDTO> getArticleBySlug(String slug) {
        var articles = articleRepository.findAllBySlug(slug);

        if (articles.isEmpty()) throw new ArticleNotFoundException(slug);

        List<ArticleGeneralResponseDTO> articleResponses = new ArrayList<>();

        articles.forEach(article -> articleResponses
                .add(modelMapper.map(article, ArticleGeneralResponseDTO.class)));

        return articleResponses;
    }

    static class ArticleNotFoundException extends IllegalArgumentException {
        public ArticleNotFoundException(String slug) {
            super("Article with slug " + slug + " not found");
        }
    }
}
