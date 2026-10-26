package se.iths.productservicegroup2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a duplicate product is detected.
 * This exception is used to indicate that an attempt was made to create or add a product that already exists in the system.
 * It is annotated with @ResponseStatus to return a 409 Conflict status code when this exception is thrown.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ProductDuplicateException extends RuntimeException {

    /**
     * Constructs a new ProductDuplicateException with the specified detail message.
     *
     * @param message the detail message
     */
    public ProductDuplicateException(String message) {
        super(message);
    }
}
