package com.arriendatufinca.arriendatufinca.Services;

import java.util.stream.Collectors;

import com.arriendatufinca.arriendatufinca.DTO.UserDTO;
import com.arriendatufinca.arriendatufinca.Entities.User;
import com.arriendatufinca.arriendatufinca.Repositories.UserRepository;

public class UserService {

    private UserRepository userRepository;

    public UserDTO get(Long id) {
        return userRepository.findById(id).map(user -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setName(user.getName());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmail(user.getEmail());
            return userDTO;
        }).orElse(null);
    }

    public Iterable<UserDTO> getAll() {
        return userRepository.findAll().stream().map(user -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setName(user.getName());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmail(user.getEmail());
            return userDTO;
        }).collect(Collectors.toList());
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO save(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        userRepository.save(user);
        return userDTO;
    }
}
