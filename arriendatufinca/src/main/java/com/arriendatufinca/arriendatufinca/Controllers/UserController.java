package com.arriendatufinca.arriendatufinca.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arriendatufinca.arriendatufinca.Repositories.UserRepository;
import com.arriendatufinca.arriendatufinca.Entities.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @CrossOrigin
    @GetMapping("/{id}")
    public User get(@PathVariable Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));
    }

    @CrossOrigin
    @GetMapping("/all")
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @CrossOrigin
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    @CrossOrigin
    @PostMapping("/create")
    public User create(@RequestParam String username, @RequestParam String name, @RequestParam String lastname, @RequestParam String email) {
        User user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setLastName(lastname);
        user.setEmail(email);
        return userRepository.save(user);
    }
}
