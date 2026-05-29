package se.iths.productservicegroup2.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import se.iths.productservicegroup2.dto.ProductRequest;
import se.iths.productservicegroup2.dto.ProductStockRequest;
import se.iths.productservicegroup2.model.Product;
import se.iths.productservicegroup2.repository.ProductRepository;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        Product product1 = new Product();
        product1.setName("Laptop");
        product1.setStock(10);
        product1.setPrice(BigDecimal.valueOf(15000));
        product1.setDescription("Gaming laptop");

        Product product2 = new Product();
        product2.setName("Router");
        product2.setStock(5);
        product2.setPrice(BigDecimal.valueOf(2000));
        product2.setDescription("Fast router");
        repository.saveAll(List.of(product1, product2));
    }

    @Test
    void getAll_ShouldReturnAllProducts_WhenRequested() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[1].name").value("Router"));
    }

    @Test
    void getById_ShouldReturnProduct_WhenProductExists() throws Exception {
        Long id = repository.findAll().get(0).getId();

        mockMvc.perform(get("/api/products/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void create_ShouldReturnCreatedProduct_WhenAdminCreates() throws Exception {
        ProductRequest request = new ProductRequest("NewGadget", "Description", BigDecimal.valueOf(999), 20);

        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/products/add")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("NewGadget"));
    }

    @Test
    void delete_ShouldReturnNoContent_WhenAdminDeletes() throws Exception {
        Long id = repository.findAll().get(0).getId();

        mockMvc.perform(delete("/api/products/delete/{id}", id)
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN"))))
                .andExpect(status().isNoContent());

        assertTrue(repository.findById(id).isEmpty());
    }

    @Test
    void decreaseStock_ShouldReduceStock_WhenSufficient() throws Exception {
        // Get laptop, 10 in stock
        Product product = repository.findAll().stream()
                .filter(p -> p.getName().equals("Laptop"))
                .findFirst().orElseThrow();

        //  request with 3 laptops
        List<ProductStockRequest> requests = List.of(new ProductStockRequest(product.getId(), 3));

        mockMvc.perform(post("/api/products/decrease-stock")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requests)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].quantity").value(3));

        Product updatedProduct = repository.findById(product.getId()).orElseThrow();
        assertEquals(7, updatedProduct.getStock());
    }

    @Test
    void decreaseStock_ShouldReturnBadRequest_WhenInsufficient() throws Exception {
        // Get router with 5 in stock
        Product product = repository.findAll().stream()
                .filter(p -> p.getName().equals("Router"))
                .findFirst().orElseThrow();

        // request with 6
        List<ProductStockRequest> requests = List.of(new ProductStockRequest(product.getId(), 6));

        mockMvc.perform(post("/api/products/decrease-stock")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_ADMIN")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requests)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Insufficient stock for product: Router"));
    }
}
