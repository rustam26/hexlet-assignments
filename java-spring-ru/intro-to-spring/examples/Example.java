// Отмечаем класс, как контроллер
// Аннотация @RestController включает в себя аннотацию @Controller and @ResponseBody
// Упрощает создание контроллера
// При использовании @RestController, использовать аннотацию @ResponseBody нет необходимости
@RestController
// Класс контроллера
public class WelcomeController {

    // Сопоставляем GET запрос на адрес /users с обработчиком
    @GetMapping("/users")
    // Обработчик
    // Привязываем параметр запроса к параметру метода
    // и задаём значение по умолчанию
    public String welcomeUser(@RequestParam(value = "name", defaultValue = "anonimus") String name) {
        return String.format("Welcome, %s!", name);
    }
}
