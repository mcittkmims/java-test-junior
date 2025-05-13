package com.java.test.junior;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.test.junior.mapper.product.ProductMapper;
import com.java.test.junior.mapper.user.UserMapper;
import com.java.test.junior.model.product.Product;
import com.java.test.junior.model.product.ProductDTO;
import com.java.test.junior.model.user.User;
import com.java.test.junior.model.user.UserPrincipal;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setup(@Autowired UserMapper userMapper) {
        User admin = new User();
        admin.setUsername("admin1");
        admin.setPassword(new BCryptPasswordEncoder(12).encode("password")); // Encode the password
        admin.setRole("ROLE_ADMIN");
        userMapper.insertUser(admin);
    }

    @BeforeEach
    void setupSecurityContext(@Autowired UserMapper userMapper, @Autowired ProductMapper productMapper) {
        User admin = userMapper.findByUsername("admin1");

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

    @Test
    void testAddProductWithInvalidData() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("");
        productDTO.setPrice(-10.0);
        productDTO.setDescription("Test Description");

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetNonExistentProduct() throws Exception {
        mockMvc.perform(get("/products/999")) // Non-existent ID
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateNonExistentProduct() throws Exception {
        ProductDTO updatedProductDTO = new ProductDTO();
        updatedProductDTO.setName("Updated Product");
        updatedProductDTO.setPrice(120.0);
        updatedProductDTO.setDescription("Updated Description");

        mockMvc.perform(put("/products/999") // Non-existent ID
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProductDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteNonExistentProduct() throws Exception {
        mockMvc.perform(delete("/products/999")) // Non-existent ID
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetProductsWithInvalidPagination() throws Exception {
        mockMvc.perform(get("/products")
                        .param("page", "0")
                        .param("page_size", "10"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/products")
                        .param("page", "1")
                        .param("page_size", "200"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUnauthorizedAccess() throws Exception {
        SecurityContextHolder.clearContext();
        
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ProductDTO())))
                .andExpect(status().isForbidden());
    }

    @Test
    void testMissingRequiredParameter() throws Exception {
        // Assuming the search endpoint requires a 'name' parameter but we don't provide it
        mockMvc.perform(get("/products/search"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"));
    }

    @Test
    void testInvalidJwtToken() throws Exception {
        SecurityContextHolder.clearContext();
        
        mockMvc.perform(get("/products/1")
                .header("Authorization", "Bearer invalid.jwt.token"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value("FORBIDDEN"));
    }
}