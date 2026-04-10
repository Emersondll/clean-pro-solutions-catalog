package io.github.emersondll.catalog.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Testes unitários para {@link NotFoundException}.
 *
 * @author Emerson DLL
 * @since 1.0.0
 */
@DisplayName("Testes para NotFoundException")
class NotFoundExceptionTest {

    private static final String ERROR_MESSAGE = "Serviço não encontrado com ID: 123";

    @Nested
    @DisplayName("Construtores")
    class Constructors {

        @Test
        @DisplayName("Deve criar exceção com mensagem")
        void deveCriarExcecaoComMensagem() {
            final NotFoundException exception = new NotFoundException(ERROR_MESSAGE);

            assertThat(exception.getMessage()).isEqualTo(ERROR_MESSAGE);
            assertThat(exception.getCause()).isNull();
        }

        @Test
        @DisplayName("Deve criar exceção com mensagem e causa")
        void deveCriarExcecaoComMensagemECausa() {
            final RuntimeException rootCause = new RuntimeException("Causa raiz");
            final NotFoundException exception = new NotFoundException(ERROR_MESSAGE, rootCause);

            assertThat(exception.getMessage()).isEqualTo(ERROR_MESSAGE);
            assertThat(exception.getCause()).isEqualTo(rootCause);
        }
    }

    @Nested
    @DisplayName("Instanciação")
    class Instantiation {

        @Test
        @DisplayName("Deve ser RuntimeException")
        void deveSerRuntimeException() {
            final NotFoundException exception = new NotFoundException(ERROR_MESSAGE);

            assertThat(exception).isInstanceOf(RuntimeException.class);
        }

        @Test
        @DisplayName("Deve ser possível lançar e capturar")
        void deveSerPossivelLancarECapturar() {
            assertThatThrownBy(() -> {
                throw new NotFoundException(ERROR_MESSAGE);
            })
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ERROR_MESSAGE);
        }
    }
}
