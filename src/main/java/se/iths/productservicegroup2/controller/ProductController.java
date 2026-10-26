package se.iths.productservicegroup2.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.iths.productservicegroup2.dto.ProductInfo;
import se.iths.productservicegroup2.dto.ProductRequest;
import se.iths.productservicegroup2.dto.ProductResponse;
import se.iths.productservicegroup2.dto.ProductStockRequest;
import se.iths.productservicegroup2.service.ProductService;

import java.util.List;

/**
 * REST controller for managing products.
 * Provides endpoints for retrieving, creating, deleting, and updating product stock.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Validated
public class ProductController {
    private final ProductService productService;

    /**
     * Retrieves all products.
     *
     * @return a ResponseEntity containing a list of ProductResponse objects
     */
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return a ResponseEntity containing the ProductResponse for the requested product
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /**
     * Creates a new product.
     *
     * @param request the ProductRequest containing the details of the product to create
     * @return a ResponseEntity containing the created ProductResponse
     */
    @PostMapping("/add")
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Decreases the stock of the specified products.
     *
     * @param request the list of products and their requested quantities
     * @return a ResponseEntity containing the updated ProductInfo objects
     */
    @PostMapping("/decrease-stock")
    public ResponseEntity<List<ProductInfo>> decreaseStock(
            @RequestBody
            @NotEmpty(message = "Product list cannot be empty")
            List<@NotNull @Valid ProductStockRequest> request) {
        List<ProductInfo> response = productService.decreaseStock(request);
        return ResponseEntity.ok(response);
    }
}
