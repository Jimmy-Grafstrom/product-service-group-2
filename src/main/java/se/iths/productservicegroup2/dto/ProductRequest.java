package se.iths.productservicegroup2.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Name must not be blank")
        @Size(max = 100, message = "Name must not exceed 100 characters")
        String name,
        @NotBlank
        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description,
        @NotNull(message = "Price must not be null")
        @Positive(message = "Price must be positive")
        BigDecimal price,
        @Min(value = 0, message = "quantity must be zero or positive")
        int quantity
) {
}
