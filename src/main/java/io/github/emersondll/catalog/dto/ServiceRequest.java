package io.github.emersondll.catalog.dto;

import io.github.emersondll.catalog.enumerations.ServiceType;

public record ServiceRequest(
        String name,
        String description,
        Double basePrice,
        Integer durationInHours,
        ServiceType type
) {}
