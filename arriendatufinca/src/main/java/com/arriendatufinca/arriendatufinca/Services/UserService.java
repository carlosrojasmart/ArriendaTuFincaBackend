package com.arriendatufinca.arriendatufinca.Services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arriendatufinca.arriendatufinca.DTO.UserDTO;
import com.arriendatufinca.arriendatufinca.Entities.User;
import com.arriendatufinca.arriendatufinca.Enums.StatusEnum;
import com.arriendatufinca.arriendatufinca.Repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;

    public UserDTO get(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        UserDTO userDTO = new UserDTO();
        if(userOptional != null) {
            userDTO = modelMapper.map(userOptional.get(), UserDTO.class);
        }
        return userDTO;
    }

    public Iterable<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
        return userDTOs;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO save(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setStatus(StatusEnum.ACTIVE);
        userRepository.save(user);
        userDTO.setId(user.getId());
        return userDTO;
    }
}
