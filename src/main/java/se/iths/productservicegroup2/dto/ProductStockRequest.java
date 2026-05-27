package se.iths.productservicegroup2.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductStockRequest(
        @NotNull(message = "Product ID is required")
        Long id,
        @Positive(message = "Must be a positive number")
        int quantity
) {
}
