package exercise.controller;

import exercise.dto.ArticleDTO;
import exercise.model.Article;
import exercise.repository.ArticleRepository;

import exercise.repository.CategoryRepository;
import org.springframework.web.bind.annotation.*;


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
