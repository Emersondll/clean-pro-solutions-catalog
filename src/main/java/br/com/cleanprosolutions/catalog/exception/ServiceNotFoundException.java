package br.com.cleanprosolutions.catalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested service is not found in the catalog.
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServiceNotFoundException extends RuntimeException {

    /**
     * Constructs the exception with the missing service ID.
     *
     * @param id the missing service identifier
     */
    public ServiceNotFoundException(final String id) {
        super("Catalog service not found with ID: " + id);
    }
}
