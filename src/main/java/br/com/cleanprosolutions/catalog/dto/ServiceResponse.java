package br.com.cleanprosolutions.catalog.dto;

import br.com.cleanprosolutions.catalog.enumerations.ServiceCategory;
import br.com.cleanprosolutions.catalog.enumerations.ServiceType;

import java.math.BigDecimal;

/**
 * Response DTO representing a catalog service.
 *
 * @param id                  MongoDB unique identifier
 * @param name                service name
 * @param description         service description
 * @param basePrice           base price
 * @param durationInHours     estimated duration in hours
 * @param type                service type
 * @param category            service category
 * @param estimatedPriceRange estimated price range
 * @param active              whether the service is active
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
public record ServiceResponse(
        String id,
        String name,
        String description,
        BigDecimal basePrice,
        Integer durationInHours,
        ServiceType type,
        ServiceCategory category,
        PriceRange estimatedPriceRange,
        boolean active
) {}
