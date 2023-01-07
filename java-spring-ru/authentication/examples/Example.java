@Configuration
// Подключаем поддержку Spring Security
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    // Переопределяем схему аутентификации
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
            // По умолчанию spring boot даёт доступ к любому url любому аутентифицированному пользователю
            // Мы должны переопределить это повдение
            // Определяем доступы к url
            .authorizeRequests()
                // Доступ к корню сайта доступен всем пользователям
                .antMatchers("/").permitAll()
                // Доступ ко всем осталным url даётся только аутентифицированным пользователям
                .anyRequest().authenticated()
                // Используем базовую аутентификацию
                // Имя пользователя и пароль передаются в заголовке в зашифрованном виде
                .and().httpBasic();
    }
}
