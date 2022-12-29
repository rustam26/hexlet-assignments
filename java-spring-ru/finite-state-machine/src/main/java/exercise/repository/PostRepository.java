package exercise.repository;

import exercise.model.Post;
import exercise.model.PostState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    Iterable<Post> findAllByState(PostState state);

    Post findByTitle(String title);
}
