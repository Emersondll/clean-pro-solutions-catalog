package io.github.emersondll.catalog.exception;

/**
 * Exceção lançada quando um recurso solicitado não é encontrado.
 *
 * <p>Segue o padrão Custom Exception do Spring para mapeamento correto
 * de erros de negócio em respostas HTTP.</p>
 *
 * @author Emerson DLL
 * @since 1.0.0
 */
public class NotFoundException extends RuntimeException {

    /**
     * Construtor com mensagem de erro.
     *
     * @param message mensagem descritiva do erro
     */
    public NotFoundException(final String message) {
        super(message);
    }

    /**
     * Construtor com mensagem e causa raiz.
     *
     * @param message mensagem descritiva do erro
     * @param cause   exceção que originou este erro
     */
    public NotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
