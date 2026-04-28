package br.com.cleanprosolutions.catalog.enumerations;

/**
 * Enum representing the service types offered by Clean Pro Solutions.
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
public enum ServiceType {

    /** Standard residential cleaning service. */
    RESIDENTIAL("Residential Cleaning"),

    /** Commercial cleaning for offices and businesses. */
    COMMERCIAL("Commercial Cleaning"),

    /** Heavy-duty cleaning after construction or renovation. */
    POST_CONSTRUCTION("Post-Construction Cleaning"),

    /** Deep and detailed cleaning service. */
    DEEP_CLEANING("Deep Cleaning");

    private final String description;

    ServiceType(final String description) {
        this.description = description;
    }

    /**
     * Returns the human-readable description of the service type.
     *
     * @return service type description
     */
    public String getDescription() {
        return description;
    }
}
