package exercise.controller;

import exercise.model.User;
import exercise.repository.UserRepository;
import exercise.model.UserRole;
import exercise.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping(path = "")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public User getUser(@PathVariable long id) {

        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @PostMapping(path = "")
    public User createUser(@RequestBody User user) {
        String password = user.getPassword();
        String encodedPassword = encoder.encode(password);
        user.setPassword(encodedPassword);
        user.setRole(UserRole.USER);

        return userRepository.save(user);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUser(@PathVariable long id) {

        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

        userRepository.delete(user);
    }
}
