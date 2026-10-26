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

/**
 * Service class for managing products.
 * Provides methods for retrieving, creating, deleting, and updating product stock.
 */
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    /**
     * Retrieves all products from the repository.
     *
     * @return a list of ProductResponse objects representing all products
     */
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return the ProductResponse corresponding to the product
     * @throws ProductNotFoundException if no product with the given ID is found
     */
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return mapper.toDto(product);
    }

    /**
     * Creates a new product.
     *
     * @param request the request containing the product details
     * @return the ProductResponse corresponding to the created product
     * @throws ProductDuplicateException if a product with the same name already exists
     */
    public ProductResponse createProduct(ProductRequest request) {
        if (productRepository.existsByName(request.name())) {
            throw new ProductDuplicateException("Product with name: " + request.name() + " already exists.");
        }
        Product entity = mapper.toEntity(request);
        Product savedProduct = productRepository.save(entity);
        return mapper.toDto(savedProduct);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete
     * @throws ProductNotFoundException if no product with the given ID is found
     */
    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    /**
     * Decreases the stock of the specified products.
     * Transactional ensures that the operation is atomic and will roll back in case of any exceptions.
     *
     * @param requestedProducts the list of products and their requested quantities
     * @return the list of product info for the updated products
     * @throws ProductNotFoundException if a product is not found
     * @throws InsufficientStockException if there is insufficient stock for a product
     */
    @Transactional
    public List<ProductInfo> decreaseStock(List<ProductStockRequest> requestedProducts) {

        List<Long> idList = requestedProducts.stream()
                .map(ProductStockRequest::id)
                .toList();

        List<Product> products = productRepository.findAllById(idList);

        if (products.size() != requestedProducts.size()) {
            throw new ProductNotFoundException("Requested " + requestedProducts.size() +
                    " products, but only found " + products.size() + " in database.");
        }

        List<ProductInfo> responseList = new ArrayList<>();

        for (ProductStockRequest request : requestedProducts) {

            Product product = products.stream()
                    .filter(p -> p.getId().equals(request.id()))
                    .findFirst()
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + request.id()));

            if (request.quantity() > product.getStock()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }

            product.setStock(product.getStock() - request.quantity());

            responseList.add(mapper.toInfo(product, request.quantity()));
        }
        return responseList;
    }
}
