package exercise.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

// BEGIN

// END
@Entity(name = "person")
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // BEGIN
    private String firstName;

    private String lastName;
    // END
}
