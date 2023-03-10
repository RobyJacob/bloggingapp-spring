package com.example.bloggingapp.articles;

import com.example.bloggingapp.articles.dtos.ArticleGeneralResponseDTO;
import com.example.bloggingapp.articles.dtos.ArticleResponseDTO;
import com.example.bloggingapp.articles.dtos.CreateArticleRequestDTO;
import com.example.bloggingapp.articles.dtos.UpdateArticleRequestDTO;
import com.example.bloggingapp.users.UserEntity;
import com.example.bloggingapp.users.UserService;
import com.example.bloggingapp.users.dtos.UserPrincipalDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    private final ModelMapper modelMapper;

    private final UserService userService;

    private final AtomicLong slugId = new AtomicLong(1);

    public ArticleService(ArticleRepository articleRepository,
                          ModelMapper modelMapper,
                          UserService userService) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    private String generateSlug(String title, String subTitle) {
        return title.replaceAll("\\s", "")
                .concat("-").concat(subTitle.replaceAll("\\s", ""))
                .concat("-")
                .concat(String.valueOf(slugId.getAndIncrement()));
    }

    public List<ArticleGeneralResponseDTO> getAllArticles(Pageable page, String author) {
        List<ArticleEntity> articles;

        if (Objects.nonNull(author))
            articles = articleRepository.findAllByAuthor(page,
                    modelMapper.map(userService.getUserByUsername(author), UserEntity.class));
        else
            articles = articleRepository.findAllArticles(page);

        List<ArticleGeneralResponseDTO> articleResponses = new ArrayList<>();

        articles.forEach(article -> articleResponses.add(modelMapper.map(article,
                ArticleGeneralResponseDTO.class)));

        return articleResponses;
    }

    public ArticleResponseDTO createArticle(CreateArticleRequestDTO createArticleRequestDTO,
                                            UserPrincipalDTO principalDTO) {
        String articleSlug = generateSlug(createArticleRequestDTO.getTitle(), createArticleRequestDTO.getSubTitle());

        var author = modelMapper.map(userService.getUserByUsername(principalDTO.getUsername()),
                UserEntity.class);

        var articleEntity = modelMapper.map(createArticleRequestDTO, ArticleEntity.class);
        articleEntity.setSlug(articleSlug);
        articleEntity.setAuthor(author);

        var savedArticle = articleRepository.save(articleEntity);

        return modelMapper.map(savedArticle, ArticleResponseDTO.class);
    }

    public ArticleResponseDTO getArticleBySlug(String slug) {
        var article = articleRepository.findBySlug(slug);

        if (Objects.isNull(article)) throw new ArticleNotFoundException(slug);

        return modelMapper.map(article, ArticleResponseDTO.class);
    }

    public ArticleResponseDTO updateArticle(String articleSlug,
                                                   UpdateArticleRequestDTO updateArticleRequestDTO,
                                                   UserPrincipalDTO principalDTO) {
        var author = modelMapper.map(userService.getUserByUsername(principalDTO.getUsername()),
                UserEntity.class);

        var article = articleRepository.findBySlugAndAuthor(articleSlug, author);

        if (Objects.isNull(article)) throw new ArticleNotFoundException(articleSlug);

        if (Objects.nonNull(updateArticleRequestDTO.getBody())) {
            article.setBody(updateArticleRequestDTO.getBody());
        }

        var updatedArticle = articleRepository.save(article);

        return modelMapper.map(updatedArticle, ArticleResponseDTO.class);
    }

    static class ArticleNotFoundException extends IllegalArgumentException {
        public ArticleNotFoundException(String slug) {
            super("Article with slug id " + slug + " not found");
        }
    }
}
