// Модель компании

// Указываем, что класс является сущностью
@Entity
public class Company {

    // Поле является первичным ключом, автогенерируемым
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    // Геттеры и сеттеры
}

// Репозиторий

// Репозиторий - это интерфейс, который мы определяем для доступа к данным
// Запросы создаются автоматически из имен методов
@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {

    Company findById(long id);
}


// Контроллер
@RestController
@RequestMapping("/companies")
public class PeopleController {

    @Autowired
    // Автоматически получаем репозиторий
    private CompanyRepository companyRepository;

    @GetMapping(path = "/{id}")
    public Company getCompany(@PathVariable long id) {
        // ищем сущность в базе по её id
        return this.companyRepository.findById(id);
    }

    @GetMapping(path = "")
    public Iterable<Company> getCompanies() {
        // получаем все сущности из базы
        return this.companyRepository.findAll();
    }

    @PostMapping(path = "")
    // Привязываем параметр метода к телу запроса
    public void createCompany(@RequestBody Company company) {
        // добавляем новую сущность в базу
        this.companyRepository.save(сompany);
    }

    @DeleteMapping(path = "/{id}")
    // Привязываем параметр метода к значению плейсхолдера
    public void deleteCompany(@PathVariable long id) {
        // удаляем сущность из базы по её id
        this.companyRepository.deleteById(id);
    }
}
