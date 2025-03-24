package com.arriendatufinca.arriendatufinca.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arriendatufinca.arriendatufinca.Services.UserService;
import com.arriendatufinca.arriendatufinca.DTO.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @CrossOrigin
    @GetMapping("/{id}")
    public UserDTO get(@PathVariable Long id) throws Exception {
        return userService.get(id);
    }

    @CrossOrigin
    @GetMapping("/all")
    public Iterable<UserDTO> getAll() {
        return userService.getAll();
    }

    @CrossOrigin
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @CrossOrigin
    @PostMapping("/create")
    public UserDTO create(@RequestParam String username, @RequestParam String name, @RequestParam String lastname, @RequestParam String email) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setName(name);
        userDTO.setLastName(lastname);
        userDTO.setEmail(email);
        return userService.save(userDTO);
    }
}
