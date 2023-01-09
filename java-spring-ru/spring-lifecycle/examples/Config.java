import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Аннотация @Configuration указывает, что класс содержит методы, создающие бины
@Configuration
public class MyApplicationConfig {

    // Аннотация @Bean указывает, что метод возвращает бин, который нужно добавить в контекст
    // Spring будет вызывать методы этого класса, помеченные аннотацией @Bean
    // и полученные объекты добавлять в контекст
    @Bean
    // В методе мы сами создаем новый экземпляр класса и возвращаем его
    // Внутри метода можно использовать любую логику, например условия
    // Будет создан и добавлен в контекст бин с именем метода - "getCat"
    public Cat getCat() {
        return new Cat();
    }
}
