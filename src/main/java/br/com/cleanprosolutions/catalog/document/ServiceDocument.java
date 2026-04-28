package br.com.cleanprosolutions.catalog.document;

import br.com.cleanprosolutions.catalog.dto.PriceRange;
import br.com.cleanprosolutions.catalog.enumerations.ServiceCategory;
import br.com.cleanprosolutions.catalog.enumerations.ServiceType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * MongoDB document representing a service in the catalog.
 *
 * <p>Follows the Rich Domain Model pattern with defensive copy support.
 * Includes category classification, estimated price range, and active status
 * to support dynamic pricing and service filtering.</p>
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
@Document(collection = "services")
public final class ServiceDocument implements Cloneable {

    @Id
    private final String id;
    private final String name;
    private final String description;
    private final BigDecimal basePrice;
    private final Integer durationInHours;
    private final ServiceType type;

    /** Service category for filtering and grouping. */
    private final ServiceCategory category;

    /** Estimated price range for dynamic pricing display. */
    private final PriceRange estimatedPriceRange;

    /** Whether the service is currently active and available for booking. */
    private final boolean active;

    /**
     * Full constructor for ServiceDocument.
     *
     * @param id                  unique identifier (null for new records)
     * @param name                service name
     * @param description         detailed description
     * @param basePrice           base price
     * @param durationInHours     estimated duration in hours
     * @param type                service type enum
     * @param category            service category
     * @param estimatedPriceRange estimated price range
     * @param active              whether the service is active
     */
    public ServiceDocument(
            final String id,
            final String name,
            final String description,
            final BigDecimal basePrice,
            final Integer durationInHours,
            final ServiceType type,
            final ServiceCategory category,
            final PriceRange estimatedPriceRange,
            final boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.durationInHours = durationInHours;
        this.type = type;
        this.category = category;
        this.estimatedPriceRange = estimatedPriceRange;
        this.active = active;
    }

    public String id() { return id; }
    public String name() { return name; }
    public String description() { return description; }
    public BigDecimal basePrice() { return basePrice; }
    public Integer durationInHours() { return durationInHours; }
    public ServiceType type() { return type; }
    public ServiceCategory category() { return category; }
    public PriceRange estimatedPriceRange() { return estimatedPriceRange; }
    public boolean active() { return active; }

    /**
     * Creates a defensive copy of this document.
     *
     * @return a new instance with the same values
     */
    public ServiceDocument copy() {
        return new ServiceDocument(id, name, description, basePrice, durationInHours, type,
                category, estimatedPriceRange, active);
    }

    @Override
    public ServiceDocument clone() {
        return copy();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        final ServiceDocument that = (ServiceDocument) other;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ServiceDocument{id='" + id + "', name='" + name + "', type=" + type +
                ", category=" + category + ", active=" + active + '}';
    }

    /**
     * Returns a new builder for constructing a {@link ServiceDocument}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link ServiceDocument}.
     */
    public static class Builder {
        private String id;
        private String name;
        private String description;
        private BigDecimal basePrice;
        private Integer durationInHours;
        private ServiceType type;
        private ServiceCategory category;
        private PriceRange estimatedPriceRange;
        private boolean active = true;

        private Builder() {}

        public Builder id(final String id) { this.id = id; return this; }
        public Builder name(final String name) { this.name = name; return this; }
        public Builder description(final String description) { this.description = description; return this; }
        public Builder basePrice(final BigDecimal basePrice) { this.basePrice = basePrice; return this; }
        public Builder durationInHours(final Integer durationInHours) { this.durationInHours = durationInHours; return this; }
        public Builder type(final ServiceType type) { this.type = type; return this; }
        public Builder category(final ServiceCategory category) { this.category = category; return this; }
        public Builder estimatedPriceRange(final PriceRange estimatedPriceRange) { this.estimatedPriceRange = estimatedPriceRange; return this; }
        public Builder active(final boolean active) { this.active = active; return this; }

        public ServiceDocument build() {
            return new ServiceDocument(id, name, description, basePrice, durationInHours,
                    type, category, estimatedPriceRange, active);
        }
    }
}
