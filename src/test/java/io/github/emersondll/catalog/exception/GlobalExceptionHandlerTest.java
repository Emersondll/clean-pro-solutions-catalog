package io.github.emersondll.catalog.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes unitários para {@link GlobalExceptionHandler}.
 *
 * <p>Valida o tratamento centralizado de exceções.</p>
 *
 * @author Emerson DLL
 * @since 1.0.0
 */
@DisplayName("Testes para GlobalExceptionHandler")
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Nested
    @DisplayName("NotFoundException")
    class NotFoundExceptionHandling {

        @Test
        @DisplayName("Deve retornar 404 com mensagem de erro")
        void handleNotFound_Retorna404() {
            final NotFoundException exception = new NotFoundException("Serviço não encontrado com ID: 123");

            final ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = handler.handleNotFound(exception);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().status()).isEqualTo(404);
            assertThat(response.getBody().error()).isEqualTo("Recurso não encontrado");
            assertThat(response.getBody().message()).isEqualTo("Serviço não encontrado com ID: 123");
            assertThat(response.getBody().timestamp()).isNotNull();
        }

        @Test
        @DisplayName("Deve incluir timestamp no ErrorResponse")
        void handleNotFound_DeveIncluirTimestamp() {
            final NotFoundException exception = new NotFoundException("Teste");

            final ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = handler.handleNotFound(exception);

            assertThat(response.getBody().timestamp()).isNotNull();
            assertThat(response.getBody().timestamp()).isBefore(LocalDateTime.now().plusSeconds(1));
        }
    }

    @Nested
    @DisplayName("Exception genérica")
    class GenericExceptionHandling {

        @Test
        @DisplayName("Deve retornar 500 sem expor detalhes internos")
        void handleGenericException_Retorna500SemDetalhes() {
            final Exception exception = new RuntimeException("Detalhes internos sensíveis");

            final ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
                    handler.handleGenericException(exception);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().status()).isEqualTo(500);
            assertThat(response.getBody().error()).isEqualTo("Erro interno do servidor");
            assertThat(response.getBody().message()).isEqualTo(
                    "Ocorreu um erro interno. Por favor, tente novamente mais tarde.");
            assertThat(response.getBody().message().toString()).doesNotContain("Detalhes internos");
        }

        @Test
        @DisplayName("Deve tratar NullPointerException gracefully")
        void handleGenericException_ComNullPointer_NaoLançaExcecao() {
            final Exception exception = new NullPointerException("NPE");

            final ResponseEntity<GlobalExceptionHandler.ErrorResponse> response =
                    handler.handleGenericException(exception);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Nested
    @DisplayName("ErrorResponse")
    class ErrorResponseStructure {

        @Test
        @DisplayName("ErrorResponse deve conter todos os campos")
        void errorResponseDeveConterTodosOsCampos() {
            final LocalDateTime now = LocalDateTime.now();
            final GlobalExceptionHandler.ErrorResponse error = new GlobalExceptionHandler.ErrorResponse(
                    404,
                    "Não encontrado",
                    "Recurso não existe",
                    now
            );

            assertThat(error.status()).isEqualTo(404);
            assertThat(error.error()).isEqualTo("Não encontrado");
            assertThat(error.message()).isEqualTo("Recurso não existe");
            assertThat(error.timestamp()).isEqualTo(now);
        }

        @Test
        @DisplayName("ErrorResponse deve permitir message como Map")
        void errorResponseDevePermitirMessageMap() {
            final LocalDateTime now = LocalDateTime.now();
            final Map<String, String> messages = new HashMap<>();
            messages.put("campo1", "erro1");
            messages.put("campo2", "erro2");

            final GlobalExceptionHandler.ErrorResponse error = new GlobalExceptionHandler.ErrorResponse(
                    400,
                    "Validação",
                    messages,
                    now
            );

            assertThat(error.message()).isInstanceOf(Map.class);
            @SuppressWarnings("unchecked")
            final Map<String, String> messageMap = (Map<String, String>) error.message();
            assertThat(messageMap).containsEntry("campo1", "erro1");
        }

        @Test
        @DisplayName("ErrorResponse deve ser um record imutável")
        void errorResponseDeveSerImutavel() {
            final LocalDateTime now = LocalDateTime.now();
            final GlobalExceptionHandler.ErrorResponse error = new GlobalExceptionHandler.ErrorResponse(
                    400, "Erro", "Msg", now
            );

            assertThat(error.status()).isEqualTo(400);
            assertThat(error.error()).isEqualTo("Erro");
            assertThat(error.message()).isEqualTo("Msg");
            assertThat(error.timestamp()).isEqualTo(now);
        }
    }

    @Nested
    @DisplayName("Método handleNotFound via NotFoundException")
    class NotFoundExceptionMessage {

        @Test
        @DisplayName("Deve preservar mensagem exata da exceção")
        void devePreservarMensagemExata() {
            final String message = "Serviço não encontrado com ID: xyz123";
            final NotFoundException exception = new NotFoundException(message);

            final ResponseEntity<GlobalExceptionHandler.ErrorResponse> response = handler.handleNotFound(exception);

            assertThat(response.getBody().message()).isEqualTo(message);
        }
    }
}
