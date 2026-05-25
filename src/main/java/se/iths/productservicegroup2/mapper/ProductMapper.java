package se.iths.productservicegroup2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.iths.productservicegroup2.dto.ProductInfo;
import se.iths.productservicegroup2.dto.ProductRequest;
import se.iths.productservicegroup2.dto.ProductResponse;
import se.iths.productservicegroup2.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRequest productRequest);
    ProductResponse toDto(Product product);

    @Mapping(target = "quantity", source = "quantity")
    ProductInfo toInfo(Product product, int quantity);
}
