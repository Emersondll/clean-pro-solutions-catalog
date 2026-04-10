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

    /**
     * Mensagem padrão para erros internos não especificados.
     */
    private static final String UNEXPECTED_ERROR_MESSAGE = "Ocorreu um erro interno. Por favor, tente novamente mais tarde.";

    /**
     * Trata exceções de recurso não encontrado.
     *
     * @param ex exceção lanzada quando o recurso não é encontrado
     * @return resposta HTTP 404 com mensagem de erro
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(final NotFoundException ex) {
        final ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Trata exceções de validação de campos em requisições (@Valid).
     *
     * @param ex exceção lançada quando a validação falha
     * @return resposta HTTP 400 com detalhes dos campos inválidos
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(final MethodArgumentNotValidException ex) {
        final Map<String, String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "Valor inválido",
                        (existing, replacement) -> existing
                ));

        final ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                fieldErrors,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Trata exceções de violação de restrições (ConstraintViolation).
     *
     * @param ex exceção lançada quando uma restrição de validação é violada
     * @return resposta HTTP 400 com mensagem de erro
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(final ConstraintViolationException ex) {
        final String message = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining("; "));

        final ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Violação de restrição",
                message,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Trata exceções genéricas não especificadas.
     *
     * @param ex exceção genérica
     * @return resposta HTTP 500 com mensagem genérica (sem detalhes técnicos)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(final Exception ex) {
        final ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno do servidor",
                UNEXPECTED_ERROR_MESSAGE,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
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
