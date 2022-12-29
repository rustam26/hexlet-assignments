package exercise.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.GenerationType;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Lob
    private String body;

    private PostState state = PostState.CREATED;

    // BEGIN
    public boolean publish() {
        if (state == PostState.CREATED) {
            setState(PostState.PUBLISHED);
            return true;
        }
        return false;
    }

    public boolean archive() {
        if (state == PostState.CREATED || state == PostState.PUBLISHED) {
            setState(PostState.ARCHIVED);
            return true;
        }
        return false;
    }
    // END
}
