@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "")
    public Iterable<User> getUsers() {
        return userService.findAll();
    }

    @PostMapping(path = "")
    public User createUser(@RequestBody User user) {
        return userService.registrateUser(user);
    }
}
