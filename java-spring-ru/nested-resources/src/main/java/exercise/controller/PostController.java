package exercise.controller;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;


@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping(path = "")
    public Iterable<Post> getPosts() {
        return postRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Post getPost(@PathVariable long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post" + id + "not found"));
    }

    @PostMapping(path = "")
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @PatchMapping(path = "/{id}")
    public Post updatePost(@PathVariable long id, @RequestBody Post post) {

        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found");
        }

        post.setId(id);
        return postRepository.save(post);
    }

    @DeleteMapping(path = "/{id}")
    public void deletePost(@PathVariable long id) {

        Post post = postRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        postRepository.delete(post);
    }
}
