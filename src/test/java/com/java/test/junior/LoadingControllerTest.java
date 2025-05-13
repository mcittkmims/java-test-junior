package com.java.test.junior;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.test.junior.mapper.product.ProductMapper;
import com.java.test.junior.mapper.user.UserMapper;
import com.java.test.junior.model.loading.FileSourceDTO;
import com.java.test.junior.model.user.User;
import com.java.test.junior.model.user.UserPrincipal;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoadingControllerTest extends AbstractIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setup(@Autowired UserMapper userMapper) {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(new BCryptPasswordEncoder(12).encode("password")); // Encode the password
        admin.setRole("ROLE_ADMIN");
        userMapper.insertUser(admin);
    }

    @BeforeEach
    void setupSecurityContext(@Autowired UserMapper userMapper, @Autowired ProductMapper productMapper) {
        User admin = userMapper.findByUsername("admin");

        UserDetails userPrincipal = new UserPrincipal(admin);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userPrincipal, null, userPrincipal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void testLoadingProducts() throws Exception {
        FileSourceDTO fileSourceDTO = new FileSourceDTO();
        fileSourceDTO.setFileAddress("products.csv");
        mockMvc.perform(post("/loading/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fileSourceDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testLoadingProductsWithInvalidFilePath() throws Exception {
        FileSourceDTO fileSourceDTO = new FileSourceDTO();
        fileSourceDTO.setFileAddress("invalid.csv");

        mockMvc.perform(post("/loading/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fileSourceDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testLoadingProductsUnauthorized() throws Exception {
        User user = new User();
        user.setUsername("regular_user");
        user.setPassword(new BCryptPasswordEncoder(12).encode("password"));
        user.setRole("ROLE_USER");

        UserDetails userPrincipal = new UserPrincipal(user);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userPrincipal, null, userPrincipal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        FileSourceDTO fileSourceDTO = new FileSourceDTO();
        fileSourceDTO.setFileAddress("products.csv");

        mockMvc.perform(post("/loading/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fileSourceDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testLoadingProductsWithEmptyFile() throws Exception {

        FileSourceDTO fileSourceDTO = new FileSourceDTO();
        fileSourceDTO.setFileAddress("empty.csv");


        mockMvc.perform(post("/loading/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fileSourceDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLoadingProductsWithInvalidFileStructure() throws Exception {

        FileSourceDTO fileSourceDTO = new FileSourceDTO();
        fileSourceDTO.setFileAddress("invalid_structure.csv");

        mockMvc.perform(post("/loading/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fileSourceDTO)))
                .andExpect(status().isBadRequest());
    }
}