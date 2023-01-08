package exercise.controller;

import exercise.model.User;
import exercise.repository.UserRepository;
import exercise.MessageSender;
import exercise.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageSender messageSender;

    @GetMapping(path = "")
    public Iterable<User> getPosts() {
        return userRepository.findAll();
    }

    @PostMapping(path = "")
    public User createUser(@RequestBody User user) {

        User savedUser = userRepository.save(user);

        String message = "User " + user.getName() + " has been registered";
        // BEGIN
        messageSender.sendMessage(message);
        // END
        return savedUser;
    }

    @DeleteMapping(path = "/{id}")
    public void deleteUser(@PathVariable long id) {

        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String message = "User " + userToDelete.getName() + " has been deleted";
        userRepository.delete(userToDelete);
        // BEGIN
        messageSender.sendMessage(message);
        // END

    }
}

