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

}