package se.iths.productservicegroup2.dto;

import java.time.LocalDateTime;

public record ApiErrorDto(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {
}
