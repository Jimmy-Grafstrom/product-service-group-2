package se.iths.productservicegroup2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import se.iths.productservicegroup2.repository.ProductRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository repository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void getAll() {

    }

    @Test
    void getById() {
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void create() {
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void delete() {
    }

    @Test
    void decreaseStock() {
    }
}