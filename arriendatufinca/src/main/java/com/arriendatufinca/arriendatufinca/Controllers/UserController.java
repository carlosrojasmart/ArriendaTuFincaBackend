package com.arriendatufinca.arriendatufinca.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.arriendatufinca.arriendatufinca.Services.UserService;
import com.arriendatufinca.arriendatufinca.DTO.UserDTO;


@RestController
@RequestMapping("/api/users") // Cambiado a plural para consistencia
@CrossOrigin // Puedes ponerlo aquí una vez para toda la clase
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserDTO get(@PathVariable Long id) {
        return userService.get(id);
    }

    @GetMapping("/all")
    public Iterable<UserDTO> getAll() {
        return userService.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PostMapping("/create")
    public UserDTO create(@RequestBody UserDTO userDTO) { // Cambiado a @RequestBody
        return userService.save(userDTO);
    }
}
