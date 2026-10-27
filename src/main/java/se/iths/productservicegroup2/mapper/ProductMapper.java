package se.iths.productservicegroup2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.iths.productservicegroup2.dto.ProductInfo;
import se.iths.productservicegroup2.dto.ProductRequest;
import se.iths.productservicegroup2.dto.ProductResponse;
import se.iths.productservicegroup2.model.Product;

/**
 * Mapper interface for converting between Product entities and DTOs.
 * Uses MapStruct to automatically generate the implementation.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {

    /**
     * Maps a ProductRequest DTO to a Product entity.
     * @param productRequest the ProductRequest DTO containing product details
     * @return the mapped Product entity
     */
    Product toEntity(ProductRequest productRequest);

    /**
     * Maps a Product entity to a ProductResponse DTO.
     * @param product the Product entity to map
     * @return the mapped ProductResponse DTO
     */
    ProductResponse toDto(Product product);

    /**
     * Maps a Product entity to a ProductInfo DTO.
     * @param product the Product entity to map
     * @param quantity the quantity of the product
     * @return the mapped ProductInfo DTO
     */
    ProductInfo toInfo(Product product, int quantity);
}
