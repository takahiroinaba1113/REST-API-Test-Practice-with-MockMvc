package com.restapitest.testrestapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapitest.testrestapi.controller.UserController;
import com.restapitest.testrestapi.entity.User;
import com.restapitest.testrestapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.SpringBootDependencyInjectionTestExecutionListener;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * How to test the application with Mock
 * 1. Mock the data (Entity)
 * 2. State when and thenReturn what you get from
 * 3. use mockMvc.perform to mock a HTTP request
 * and describe what is expected as the result
 */
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void getUserByIdTest() throws Exception {

        // mock the data return by the user service class
        User user = new User();

        user.setName("john");
        user.setEmail("john@gmail.com");
        user.setPhone("9800000");
        user.setGender("Male");

        when(userService.getUserById(anyInt())).thenReturn(user);
        // create a mock HTTP request to verify the expected result

        mockMvc.perform(MockMvcRequestBuilders.get("/user/12"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("john"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("9800000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Male"))
                .andExpect(status().isOk());
    }

    @Test
    public void saveUserTest() throws Exception {

        // mock the user data we want to save
        User user = new User();
        user.setId(1);
        user.setName("john");
        user.setEmail("john@gmail.com");
        user.setPhone("9800000");
        user.setGender("Male");

        when(userService.saveUser(any(User.class))).thenReturn(user);
        // create a mock HTTP request to verify the excepted result

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .content(new ObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("john"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("9800000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Male"));
    }
}
