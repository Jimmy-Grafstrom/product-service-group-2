package se.iths.productservicegroup2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when there is insufficient stock for a product.
 * This exception is used to indicate that the requested quantity of a product exceeds the available stock.
 * It is annotated with @ResponseStatus to return a 400 Bad Request status code when this exception is thrown.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientStockException extends RuntimeException {

    /**
     * Constructs a new InsufficientStockException with the specified detail message.
     *
     * @param message the detail message
     */
    public InsufficientStockException(String message) {
        super(message);
    }
}
