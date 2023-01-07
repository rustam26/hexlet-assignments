package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.ResourceNotFoundException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;


@RestController
@RequestMapping("/posts")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    // BEGIN
    @GetMapping("/{postId}/comments")
    public Iterable<Comment> getAllCommentsByPostId(@PathVariable("postId") Long postId) {
        return commentRepository.findAllByPost_Id(postId);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public Comment getCommentByCommentIdAndPostId(@PathVariable("postId") Long postId,
                                                  @PathVariable("commentId") Long commentId) {
        return commentRepository.findByIdAndPost_Id(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("comment" + commentId + "not found"));
    }

    @PostMapping("/{postId}/comments")
    public Iterable<Comment> addComment(@RequestBody Comment comment, @PathVariable Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post" + postId + "not found"));
        comment.setPost(post);
        commentRepository.save(comment);
        return commentRepository.findAllByPost_Id(postId);
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    public Iterable<Comment> updateComment(@RequestBody Comment comment,
                                           @PathVariable Long postId,
                                           @PathVariable Long commentId) {
        Comment commentOld = commentRepository.findByIdAndPost_Id(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("comment" + commentId + "not found"));
        commentOld.setContent(comment.getContent());
        commentRepository.save(commentOld);
        return commentRepository.findAllByPost_Id(postId);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public void deleteComment(@PathVariable Long postId,
                              @PathVariable Long commentId) {
        Comment comment = commentRepository.findByIdAndPost_Id(commentId, postId)
                .orElseThrow(() -> new ResourceNotFoundException("comment" + commentId + "not found"));
        commentRepository.delete(comment);
    }
    // END
}
