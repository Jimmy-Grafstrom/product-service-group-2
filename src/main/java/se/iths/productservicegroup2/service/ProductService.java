package se.iths.productservicegroup2.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.iths.productservicegroup2.dto.ProductInfo;
import se.iths.productservicegroup2.dto.ProductRequest;
import se.iths.productservicegroup2.dto.ProductResponse;
import se.iths.productservicegroup2.dto.ProductStockRequest;
import se.iths.productservicegroup2.exceptions.InsufficientStockException;
import se.iths.productservicegroup2.exceptions.ProductDuplicateException;
import se.iths.productservicegroup2.exceptions.ProductNotFoundException;
import se.iths.productservicegroup2.mapper.ProductMapper;
import se.iths.productservicegroup2.model.Product;
import se.iths.productservicegroup2.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return mapper.toDto(product);
    }

    public ProductResponse createProduct(ProductRequest request) {
        if (productRepository.existsByName(request.name())) {
            throw new ProductDuplicateException("Product already exists.");
        }
        Product entity = mapper.toEntity(request);
        Product savedProduct = productRepository.save(entity);
        return mapper.toDto(savedProduct);
    }

    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Transactional
    public List<ProductInfo> decreaseStock(List<ProductStockRequest> requestedProducts) {
        // Find all id from db
        List<Long> idList = requestedProducts.stream()
                .map(ProductStockRequest::id)
                .toList();
        // One DB request, save all products to list
        List<Product> products = productRepository.findAllById(idList);
        // Check if length matches
        if (products.size() != requestedProducts.size()) {
            throw new ProductNotFoundException("One or more products were not found");
        }
        // Empty list with responses
        List<ProductInfo> responseList = new ArrayList<>();
        // Loop through requested products
        for (ProductStockRequest request : requestedProducts) {
            // Compare ids from db and requested products. Throw exception if the ids don't match.
            Product product = products.stream()
                    .filter(p -> p.getId().equals(request.id()))
                    .findFirst()
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + request.id()));
            // Check stock
            if (request.quantity() > product.getStock()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }
            // Update stock
            product.setStock(product.getStock() - request.quantity());
            // Create response, Transactional automatically saves the updates to db
            responseList.add(mapper.toInfo(product, request.quantity()));
        }
        return responseList;
    }
}
