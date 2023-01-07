// Сущность

@Entity
public class User {

    private long id;

    private String name;

    private Integer age;
}

// Репозиторий

@Repository
public interface UserRepository extends
    // Наследуем репозиторий от этих классов
    JpaRepository<User, Long>,
    QuerydslPredicateExecutor<User>,
    QuerydslBinderCustomizer<QUser> {

    // Чтобы не возникала ошибка, нужно переопределить в репозитории метод customize
    // Если не нужно ничего кастомизировать, метод оставляем пустым
    // Класс QUser автоматически генерируется при компиляции, если добавить нужные зависимости
    // и процессоры аннотаций. Изучите зависимости в файле build.gradle
    @Override
    default void customize(QuerydslBindings bindings, QUser user) {
    }
}

// Контроллер

@RestController
@RequestMapping("/companies")
public class UsersController {

    @Autowired
    private UserRepository userRepository;


    // Ищем всех пользователей с определенным именем
    public Iterable<User> getUsersByName(String name) {
        return userRepository.findAll(QUser.user.name.eq(name));
        // eq() – равно
    }

    // Ищем всех пользователей в возрастом в заданном диапазоне
    // включая границы диапазона
    public Iterable<User> getUsersByAge(Integer minAge, Integer maxAge) {
        return userRepository.findAll(QUser.user.age.goe(minAge).and(QUser.user.age.loe(maxAge)));
        // goe() – больше либо равно
        // loe() – меньше либо равно
    }

    // Ищем всех пользователей, чьё имя не совпадает с переданным
    public Iterable<User> getUsersExcludingName(String name) {
        return repository.findAll(QUser.user.name.ne(name));
        // ne() – не равно
    }
}
