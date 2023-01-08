@Service
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        String password = user.getPassword();
        String encodedPassword = encoder.encode(password);
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }
}
