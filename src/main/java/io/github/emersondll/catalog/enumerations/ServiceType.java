package io.github.emersondll.catalog.enumerations;

/**
 * Enumeração que representa os tipos de serviços oferecidos pela Clean Pro Solutions.
 *
 * @author Emerson DLL
 * @since 1.0.0
 */
public enum ServiceType {

    /** Serviço de limpeza residencial padrão. */
    RESIDENTIAL("Limpeza Residencial"),

    /** Serviço de limpeza comercial para escritórios e comércios. */
    COMMERCIAL("Limpeza Comercial"),

    /** Serviço de limpeza pesada após construção ou reforma. */
    POST_CONSTRUCTION("Limpeza Pós-Obra"),

    /** Serviço de limpeza profunda e detalhada. */
    DEEP_CLEANING("Limpeza Profunda");

    private final String description;

    ServiceType(final String description) {
        this.description = description;
    }

    /**
     * Retorna a descrição legível do tipo de serviço.
     *
     * @return descrição do tipo de serviço
     */
    public String getDescription() {
        return description;
    }
}
