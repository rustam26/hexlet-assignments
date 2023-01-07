package exercise.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Имя пользователя, которое будет использоваться при аутентификации
    private String username;

    private String email;

    // Поле, которое содержит пароль пользователя
    // С этим паролем будет сравниваться пароль, введённый при аутентификации
    private String password;
}
