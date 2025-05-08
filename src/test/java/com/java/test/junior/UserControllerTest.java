package com.java.test.junior;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.test.junior.model.user.LoginUserDTO;
import com.java.test.junior.model.user.RegisterUserDTO;
import com.java.test.junior.model.user.UserRoles;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1) // Ensures this test runs first
    void testRegisterUser() throws Exception {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        registerUserDTO.setUsername("adrian");
        registerUserDTO.setPassword("Adri@n2005");
        registerUserDTO.setRole(UserRoles.ROLE_USER);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2) // Ensures this test runs after testRegisterUser
    void testLoginUser() throws Exception {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setUsername("adrian");
        loginUserDTO.setPassword("Adri@n2005");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserDTO)))
                .andExpect(status().isOk());
    }
}