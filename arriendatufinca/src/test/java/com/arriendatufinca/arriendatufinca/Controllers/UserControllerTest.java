package com.arriendatufinca.arriendatufinca.Controllers;

import com.arriendatufinca.arriendatufinca.DTO.UserDTO;
import com.arriendatufinca.arriendatufinca.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("carlos123");
        userDTO.setName("Carlos");
        userDTO.setLastName("Pérez");
        userDTO.setEmail("carlos@example.com");
    }

    @Test
    void userCreateTest() throws Exception {
        when(userService.save(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("carlos123"));
    }

    @Test
    void getUserByIDTest() throws Exception {
        when(userService.get(1L)).thenReturn(userDTO);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Carlos"));
    }

    @Test
    void getAllUsersTest() throws Exception {
        when(userService.getAll()).thenReturn(List.of(userDTO));

        mockMvc.perform(get("/api/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("carlos@example.com"));
    }

    @Test
    void deleteUserTest() throws Exception {
        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/api/users/delete/1"))
                .andExpect(status().isOk());
    }
}