import org.springframework.stereotype.Component;

// Аннотация @Component позволяет Spring обнаружить класс при сканировании классов
// Будет создан бин с именем "dog" и добавлен в контекст
// Имя бина совпадает с именем класса, записанным с lowerCamelCase
@Component
public class Dog {
    private String name = "Rex";

    public String getName() {
        return name;
    }
}
