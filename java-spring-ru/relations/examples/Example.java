// Модели

@Getter
@Setter
@Entity
public class Chapter {

    // Первичный автогенерируемый ключ
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    // Поле может иметь большое содержимое, например текст
    @Lob
    private String content;

    // В книге может быть множество глав
    // Но глава принадлежит одной книге
    @ManyToOne
    private Book book;
}

// Связанная сущность
@Getter
@Setter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String author;
}

// Контроллер

@RestController
@RequestMapping("/chapters")
public class ChaptersController {

    @Autowired
    private ChapterRepository chapterRepository;

    @GetMapping(path = "")
    public Iterable<Chapters> getChapters() {
        return this.chapterRepository.findAll();
    }

    @DeleteMapping(path = "/{id}")
    public void deleteChapter(@PathVariable long id) {
        this.chapterRepository.deleteById(id);
    }

    @PostMapping(path = "")
    public Chapter createChapter(@RequestBody Chapter chapter) {
        return this.chapterRepository.save(chapter);
    }

    @GetMapping(path = "/{id}")
    public Chapter getChapter(@PathVariable long id) {
        return this.chapterRepository.findById(id);
    }

    @PatchMapping(path = "/{id}")
    public Chapter updateChapter(@PathVariable long id, @RequestBody Chapter chapter) {
        chapter.setId(id);
        return this.chapterRepository.save(chapter);
    }
}
