package com.arriendatufinca.arriendatufinca.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.arriendatufinca.arriendatufinca.DTO.UserDTO;
import com.arriendatufinca.arriendatufinca.Entities.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;


public class UserControllerTest {

    UserController userController = new UserController();
    User user = new User();
    ModelMapper modelMappper = new ModelMapper();

    @Test
    public void userCreateTest() {
        try{
            UserDTO userDTO = userController.create("Test", "Test", "Testson", "Test@testson.com");
            user = modelMappper.map(userDTO, User.class);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getUserByIDTest() {
        try{
            UserDTO userDTO = userController.get(user.getId());
            User userTemp = modelMappper.map(userDTO, User.class);
            assertEquals(user.getId(), userTemp.getId());
            assertEquals(user.getName(), userTemp.getName());
            assertEquals(user.getLastName(), userTemp.getLastName());
            assertEquals(user.getEmail(), userTemp.getEmail());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void getAllUsersTest() {
        try{
            Iterable<UserDTO> userDTOs = userController.getAll();
            for (UserDTO userDTO : userDTOs) {
                User userTemp = modelMappper.map(userDTO, User.class);
                assertEquals(user.getId(), userTemp.getId());
                assertEquals(user.getName(), userTemp.getName());
                assertEquals(user.getLastName(), userTemp.getLastName());
                assertEquals(user.getEmail(), userTemp.getEmail());
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteUserTest() {
        try{
            userController.delete(user.getId());
            UserDTO userDTO = userController.get(user.getId());
            Assert.isNull(userDTO);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
