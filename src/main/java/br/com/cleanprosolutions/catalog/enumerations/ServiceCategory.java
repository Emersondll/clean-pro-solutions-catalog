package br.com.cleanprosolutions.catalog.enumerations;

/**
 * Enum representing the category of a service in the catalog.
 *
 * <p>Used for filtering and grouping services by their domain category.</p>
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
public enum ServiceCategory {

    /** General cleaning services (homes, offices). */
    CLEANING,

    /** Hydraulic repairs and installations. */
    PLUMBING,

    /** Electrical repairs and installations. */
    ELECTRICAL,

    /** Painting services (interior and exterior). */
    PAINTING,

    /** Carpentry and furniture assembly. */
    CARPENTRY,

    /** Gardening and landscaping. */
    GARDENING,

    /** Air conditioning installation and maintenance. */
    AIR_CONDITIONING,

    /** Other services not covered by the above categories. */
    OTHER
}
