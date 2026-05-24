package se.iths.productservicegroup2.dto;

import java.math.BigDecimal;

public record ProductInfo(
        Long id,
        String name,
        String description,
        BigDecimal price,
        int quantity
) {
}
