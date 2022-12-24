package exercise.controller;

import exercise.dto.ArticleDTO;
import exercise.model.Article;
import exercise.repository.ArticleRepository;

import exercise.repository.CategoryRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/articles")
public class ArticlesController {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    public ArticlesController(ArticleRepository articleRepository, CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
    }


    @GetMapping(path = "")
    public Iterable<Article> getArticles() {
        return this.articleRepository.findAll();
    }

    @DeleteMapping(path = "/{id}")
    public void deleteArticle(@PathVariable long id) {
        this.articleRepository.deleteById(id);
    }

    // BEGIN
    @PostMapping(path = "")
    public Article createArticles(@RequestBody ArticleDTO articleDTO) {
        Article article = new Article();
        article.setName(articleDTO.getName());
        article.setBody(articleDTO.getBody());
        article.setCategory(categoryRepository.findById(articleDTO.getCategory()
                .getId()));

        return articleRepository.save(article);
    }

    @PatchMapping(path = "/{id}")
    public Article updateArticle(@RequestBody ArticleDTO articleDTO, @PathVariable("id") long id) {
        Article article = articleRepository.findById(id);
        article.setName(articleDTO.getName());
        article.setBody(articleDTO.getBody());
        article.setCategory(categoryRepository.findById(articleDTO.getCategory()
                .getId()));
        return articleRepository.save(article);
    }

    @GetMapping(path = "/{id}")
    public Article getArticle(@PathVariable("id") Long id) {
        return articleRepository.findById(id)
                .orElseThrow();
    }
    // END
}
