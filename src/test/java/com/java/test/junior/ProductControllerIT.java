package com.java.test.junior;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.test.junior.mapper.product.ProductMapper;
import com.java.test.junior.mapper.user.UserMapper;
import com.java.test.junior.model.product.Product;
import com.java.test.junior.model.product.ProductDTO;
import com.java.test.junior.model.user.User;
import com.java.test.junior.model.user.UserPrincipal;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIT extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductMapper productMapper;

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

        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setPrice(50.0);
        product1.setDescription("Description for Product 1");
        product1.setUserId(admin.getId());
        productMapper.insertProduct(product1);
    }

    @Test
    void testAddProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setPrice(100.0);
        productDTO.setDescription("Test Description");

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void testGetProduct() throws Exception {
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetProductsPaginated() throws Exception {
        mockMvc.perform(get("/products?page=1&page_size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.greaterThan(0)));
    }

    @Test
    void testUpdateProduct() throws Exception {
        ProductDTO updatedProductDTO = new ProductDTO();
        updatedProductDTO.setName("Updated Product");
        updatedProductDTO.setPrice(120.0);
        updatedProductDTO.setDescription("Updated Description");

        mockMvc.perform(put("/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProductDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.price").value(120.0))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }
    
    @Test
    void testLikeProduct() throws Exception {
        mockMvc.perform(post("/products/1/like"))
                .andExpect(status().isOk());
    }

    @Test
    void testDislikeProduct() throws Exception {
        mockMvc.perform(post("/products/1/dislike"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSearchProductsByName() throws Exception {
        mockMvc.perform(get("/products/search")
                        .param("name", "Product 1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.greaterThan(0)))
                .andExpect(jsonPath("$[0].name").value("Product 1"));
    }
}