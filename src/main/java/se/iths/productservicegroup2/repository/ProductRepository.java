package se.iths.productservicegroup2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.productservicegroup2.model.Product;

/**
 * Repository interface for managing Product entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Checks if a product with the given name exists.
     *
     * @param name the name of the product to check
     * @return true if a product with the given name exists, false otherwise
     */
    boolean existsByName(String name);
}
