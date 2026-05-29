package se.iths.productservicegroup2.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.iths.productservicegroup2.model.Product;
import se.iths.productservicegroup2.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Checking if database needs to be initialized with products...");

        if (productRepository.count() == 0) {
            log.info("Database is empty. Initializing default products...");

            List<Product> defaultProducts = List.of(
                    Product.builder()
                            .name("Laptop Pro")
                            .description("A high-end laptop for developers and creators.")
                            .price(new BigDecimal("15999.00"))
                            .stock(50)
                            .build(),
                    Product.builder()
                            .name("Wireless Mouse")
                            .description("Ergonomic wireless mouse with long battery life.")
                            .price(new BigDecimal("499.00"))
                            .stock(150)
                            .build(),
                    Product.builder()
                            .name("Mechanical Keyboard")
                            .description("Tactile RGB mechanical keyboard with blue switches.")
                            .price(new BigDecimal("1299.00"))
                            .stock(80)
                            .build(),
                    Product.builder()
                            .name("4K Monitor 27\"")
                            .description("Ultra HD IPS monitor with HDR support.")
                            .price(new BigDecimal("3899.00"))
                            .stock(30)
                            .build()
            );

            productRepository.saveAll(defaultProducts);
            log.info("Successfully initialized {} products in the database.", defaultProducts.size());
        } else {
            log.info("Database already contains products. Skipping initialization.");
        }
    }
}
