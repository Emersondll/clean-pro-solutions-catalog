package io.github.emersondll.catalog.document;

import io.github.emersondll.catalog.enumerations.ServiceType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "services")
public record ServiceDocument(
        @Id String id,
        String name,
        String description,
        Double basePrice,
        Integer durationInHours,
        ServiceType type
) {}
