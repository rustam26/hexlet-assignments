package exercise.model;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;

// BEGIN
@Entity
@Getter
@Setter
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private String body;

    @ManyToOne
    private Category category;

}
// END
