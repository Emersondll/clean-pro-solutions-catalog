package br.com.cleanprosolutions.catalog.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.stream.Collectors;

/**
 * Global exception handler for the catalog service.
 *
 * <p>Formats exceptions as RFC 7807 Problem Details.</p>
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceNotFoundException.class)
    public ProblemDetail handleNotFound(final ServiceNotFoundException ex) {
        log.warn("ServiceNotFoundException: {}", ex.getMessage());
        final ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        detail.setType(URI.create("https://cleanprosolutions.com.br/errors/catalog/not-found"));
        detail.setTitle("Service Not Found");
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(final MethodArgumentNotValidException ex) {
        final String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.warn("Validation error: {}", errors);
        final ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errors);
        detail.setType(URI.create("https://cleanprosolutions.com.br/errors/catalog/validation"));
        detail.setTitle("Validation Error");
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneral(final Exception ex) {
        log.error("Unexpected error in catalog service: {}", ex.getMessage(), ex);
        final ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred in the catalog service");
        detail.setType(URI.create("https://cleanprosolutions.com.br/errors/catalog/internal"));
        detail.setTitle("Internal Server Error");
        detail.setProperty("timestamp", Instant.now());
        return detail;
    }
}
