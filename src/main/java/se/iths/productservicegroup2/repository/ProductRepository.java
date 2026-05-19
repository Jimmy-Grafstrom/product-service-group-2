package se.iths.productservicegroup2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.productservicegroup2.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
