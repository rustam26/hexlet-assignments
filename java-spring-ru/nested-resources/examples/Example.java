// Связанные сущности Автор и Книга
// У автора может быть много книг, но книга написана одним автором

// Книга принадлежит автору. Отразим эту иерархию, сделав книгу вложенным ресурсом

// GET /authors/king/books/32

public class Author {

    private long id;

    private String name;

    @JsonIgnore
    @OneToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        mappedBy = "author"
    )
    private List<Book> books;
}

public class Book {

    private long id;

    private String title;

    @Lob
    private String content;

    // Связанная сущность
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Author author;
}

// Репозиторий

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    // Можно использовать именование методов репозитория для автоматической генерации запроса
    // Поиск всех книг по имени автора
    Iterable<Book> findAllByAuthorName(String name);
    // Поиск книги по её id у конкретного автора
    Optional<Book> findByIdAndAuthorName(Long id, String name);
    // Подробнее о правилах именования методов можно прочитать в документации
}

// Контроллер

@RestController
@RequestMapping("/authors")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // GET /authors/king/books
    // Получаем все книги автора
    @GetMapping(path = "/{name}/books")
    public Iterable<Book> getAuthorBooks(@PathVariable String name) {
        return bookRepository.findAllByAuthorName(name);
    }

    // GET /authors/king/books/23
    // Получаем конкретную книгу конкретного автора
    @GetMapping(path = "/{name}/books/{bookId}")
    public Comment getBook(@PathVariable String name, @PathVariable long bookId) {

        // Ищем книгу по id у конкретного автора
        return bookRepository.findByIdAndAuthorName(name, postId)
            // Если книги с таким id у этого автора нет,
            // Возвращаем ответ с кодом 404
            .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }
}
