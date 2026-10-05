package se.iths.productservicegroup2.dto;

import se.iths.productservicegroup2.model.Category;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        int stock,
        Category category,
        String imageUrl
) {
}
