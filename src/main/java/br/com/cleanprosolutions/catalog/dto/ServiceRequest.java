package br.com.cleanprosolutions.catalog.dto;

import br.com.cleanprosolutions.catalog.enumerations.ServiceCategory;
import br.com.cleanprosolutions.catalog.enumerations.ServiceType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * Request DTO for creating or updating a catalog service.
 *
 * <p>Immutable record with Jakarta Bean Validation constraints.</p>
 *
 * @param name                service name (3–100 characters)
 * @param description         service description (max 500 characters)
 * @param basePrice           base price (positive, max 10 integer digits, 2 decimal)
 * @param durationInHours     estimated duration in hours (1–24)
 * @param type                service type
 * @param category            service category
 * @param estimatedPriceRange optional price range for dynamic display
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
public record ServiceRequest(

        @NotBlank(message = "Service name is required")
        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
        String name,

        @NotBlank(message = "Service description is required")
        @Size(max = 500, message = "Description must have at most 500 characters")
        String description,

        @NotNull(message = "Base price is required")
        @DecimalMin(value = "0.01", message = "Base price must be greater than zero")
        @Digits(integer = 10, fraction = 2, message = "Base price must have at most 10 integer digits and 2 decimal places")
        BigDecimal basePrice,

        @NotNull(message = "Duration in hours is required")
        @Min(value = 1, message = "Duration must be at least 1 hour")
        @Max(value = 24, message = "Duration cannot exceed 24 hours")
        Integer durationInHours,

        @NotNull(message = "Service type is required")
        ServiceType type,

        ServiceCategory category,

        PriceRange estimatedPriceRange
) {}
