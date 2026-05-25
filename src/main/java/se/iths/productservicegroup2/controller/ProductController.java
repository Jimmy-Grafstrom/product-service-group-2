package se.iths.productservicegroup2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.iths.productservicegroup2.dto.ProductInfo;
import se.iths.productservicegroup2.dto.ProductRequest;
import se.iths.productservicegroup2.dto.ProductResponse;
import se.iths.productservicegroup2.dto.ProductStockRequest;
import se.iths.productservicegroup2.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/decrease-stock")
    public ResponseEntity<List<ProductInfo>> decreaseStock(@RequestBody List<ProductStockRequest> request) {
        List<ProductInfo> response = productService.decreaseStock(request);
        return ResponseEntity.ok(response);
    }
}
