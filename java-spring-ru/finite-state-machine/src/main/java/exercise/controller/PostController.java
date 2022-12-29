package exercise.controller;

import exercise.model.Post;
import exercise.model.PostState;
import exercise.repository.PostRepository;
import exercise.PostNotFoundException;
import exercise.UnprocessableEntityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping(path = "")
    public Iterable<Post> getPosts() {
        return postRepository.findAllByState(PostState.PUBLISHED);
    }

    @PostMapping(path = "")
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @GetMapping(path = "/{id}")
    public Post getPost(@PathVariable long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @PatchMapping(path = "/{id}/publish")
    public Post publish(@PathVariable long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        // Вызывается событие publish
        // Если переход возможен, устанавливаем новое состояние и сохраняем пост
        if (post.publish()) {
            return postRepository.save(post);
        }

        throw new UnprocessableEntityException("Publishing is not possible");
    }

    @PatchMapping(path = "/{id}/archive")
    public Post archive(@PathVariable long id) {
        // BEGIN
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        if (post.archive()) {
            return postRepository.save(post);
        }

        throw new UnprocessableEntityException("Archived is not possible");
        // END
    }
}
