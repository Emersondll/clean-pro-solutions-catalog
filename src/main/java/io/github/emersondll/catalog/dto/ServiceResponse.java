package io.github.emersondll.catalog.dto;

import io.github.emersondll.catalog.enumerations.ServiceType;

public record ServiceResponse(
        String id,
        String name,
        String description,
        Double basePrice,
        Integer durationInHours,
        ServiceType type
) {}
