@RestController
public class PetController {

    // Получаем из контекста
    @Autowired
    Cat cat;

    @Autowired
    Dog dog;

    @GetMapping("/pet")
    public String root() {
        return "Dog " + dog.getName() + "and cat " + cat.getName();
    }
}
