// Перечисление возможных ролей

public enum UserRole {
    ADMIN,
    USER
}

// Модель пользователя

public class User {

    private long id;

    private String username;

    private String email;

    private String password;

    // Поле, в котором будет находиться роль пользователя
    private UserRole role;

    // Геттеры и сеттеры
}

// User Details Service

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    // Репозиторий с пользователями
    private UserRepository repository;

    // Переопределяем метод loadUserByUsername()
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Получаем пользователя из репозитория
        User user = repository.findByUsername(email)
            .orElseThrow(() -> new UserNotFoundException("user not found"));

        // Получаем роль пользователя
        // String userRole = ...

        // Создаём список полномочий
        List<SimpleGrantedAuthority> authorities = List.of(
            new SimpleGrantedAuthority(userRole)
        );

        // Создаём новый объект org.springframework.security.core.userdetails.User
        // Передаём туда пароль,
        // имя пользователя (или те данные, которые используются вместо него, например почту или телефон),
        // и список полномочий, которым будет обладать пользователь
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(), user.getPassword(), authorities
        );
    }
}

// Настраиваем авторизацию

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    // Переопределяем метод configure
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().and().sessionManagement().disable();

        http
            .authorizeRequests()
                // Регистрация доступна всем пользователям
                .antMatchers(POST, "/users").permitAll()
                // Доступ к url /hello доступен аутентифицированным пользователям
                // с полномочиями "USER" или "ADMIN"
                .antMatchers("/hello").hasAnyAuthority(UserRole.USER.name(), UserRole.ADMIN.name())
                // Доступ к url /admin и всем вложенным url, например /admin/hello
                // разрешён только с полномочиями "ADMIN"
                .antMatchers("/admin/**").hasAuthority(UserRole.ADMIN.name())
                .and().httpBasic();
    }
}
