package se.iths.productservicegroup2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.iths.productservicegroup2.dto.ProductRequest;
import se.iths.productservicegroup2.dto.ProductResponse;
import se.iths.productservicegroup2.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "quantity", target = "stock")
    Product toEntity(ProductRequest productRequest);
    @Mapping(source = "stock", target = "quantity") //TODO ta bort
    ProductResponse toDto(Product product);
}
