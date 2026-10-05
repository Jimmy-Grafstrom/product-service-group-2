package se.iths.productservicegroup2.dto;

import jakarta.validation.constraints.*;
import se.iths.productservicegroup2.model.Category;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Name must not be blank")
        @Size(max = 100, message = "Name must not exceed 100 characters")
        @Pattern(regexp = "^[a-zA-Z0-9åäöÅÄÖ _-]+$", message = "Only letters, numbers, spaces, hyphens and underscores allowed")
        String name,

        @NotBlank
        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description,

        @NotNull(message = "Price must not be null")
        @Positive(message = "Price must be positive")
        @Digits(integer = 8, fraction = 2, message = "Price must have at most 8 integer digits and 2 decimal places")
        BigDecimal price,

        @Min(value = 0, message = "Stock must be zero or positive")
        int stock,

        @NotNull(message = "Category must not be null")
        Category category,

        @Size(max = 500, message = "Image URL must not exceed 500 characters")
        String imageUrl
) {
}
