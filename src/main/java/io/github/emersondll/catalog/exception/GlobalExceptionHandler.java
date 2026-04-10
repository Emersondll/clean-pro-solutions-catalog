package io.github.emersondll.catalog.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Handler global de exceções para a API.
 *
 * <p>Centraliza o tratamento de erros seguindo o padrão Controller Advice,
 * garantindo respostas consistentes e profissional para todos os endpoints.</p>
 *
 * @author Emerson DLL
 * @since 1.0.0
 * @see NotFoundException
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String NOT_FOUND_ERROR = "Recurso não encontrado";
    private static final String VALIDATION_ERROR = "Erro de validação";
    private static final String CONSTRAINT_VIOLATION_ERROR = "Violação de restrição";
    private static final String INTERNAL_ERROR = "Erro interno do servidor";
    private static final String UNEXPECTED_ERROR_MESSAGE = "Ocorreu um erro interno. Por favor, tente novamente mais tarde.";
    private static final String DEFAULT_FIELD_ERROR_MESSAGE = "Valor inválido";

    /**
     * Trata exceções de recurso não encontrado.
     *
     * @param ex exceção lanzada quando o recurso não é encontrado
     * @return resposta HTTP 404 com mensagem de erro
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(final NotFoundException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                NOT_FOUND_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Trata exceções de validação de campos em requisições (@Valid).
     *
     * @param exception exceção lançada quando a validação falha
     * @return resposta HTTP 400 com detalhes dos campos inválidos
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(final MethodArgumentNotValidException exception) {
        final Map<String, String> validationErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : DEFAULT_FIELD_ERROR_MESSAGE,
                        (existingError, replacementError) -> existingError
                ));

        final ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                VALIDATION_ERROR,
                validationErrors,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Trata exceções de violação de restrições (ConstraintViolation).
     *
     * @param exception exceção lançada quando uma restrição de validação é violada
     * @return resposta HTTP 400 com mensagem de erro
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(final ConstraintViolationException exception) {
        final String constraintViolationMessage = exception.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining("; "));

        final ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                CONSTRAINT_VIOLATION_ERROR,
                constraintViolationMessage,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Trata exceções genéricas não especificadas.
     *
     * @param exception exceção genérica
     * @return resposta HTTP 500 com mensagem genérica (sem detalhes técnicos)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(final Exception exception) {
        final ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                INTERNAL_ERROR,
                UNEXPECTED_ERROR_MESSAGE,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Record que representa a estrutura de resposta de erro padronizada.
     *
     * @param status  código HTTP do erro
     * @param error   tipo/descrição do erro
     * @param message mensagem detalhada ou mapa de erros
     * @param timestamp data e hora em que o erro ocorreu
     */
    public record ErrorResponse(
            int status,
            String error,
            Object message,
            LocalDateTime timestamp
    ) {}
}
