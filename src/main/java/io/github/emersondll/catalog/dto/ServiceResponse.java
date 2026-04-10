package io.github.emersondll.catalog.dto;

import io.github.emersondll.catalog.enumerations.ServiceType;

import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) para respostas de serviços.
 *
 * <p>Utiliza o padrão Record para representar dados imutáveis de saída.
 * Encapsula todos os dados retornados ao cliente sobre um serviço.</p>
 *
 * @author Emerson DLL
 * @since 1.0.0
 * @see ServiceRequest
 */
public record ServiceResponse(

        String id,

        String name,

        String description,

        BigDecimal basePrice,

        Integer durationInHours,

        ServiceType type
) {}
