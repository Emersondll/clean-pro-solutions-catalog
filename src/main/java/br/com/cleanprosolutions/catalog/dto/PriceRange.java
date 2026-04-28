package br.com.cleanprosolutions.catalog.dto;

import java.math.BigDecimal;

/**
 * Immutable value object representing an estimated price range for a service.
 *
 * <p>Used to indicate price boundaries without committing to a fixed value,
 * supporting dynamic pricing models.</p>
 *
 * @param minPrice minimum estimated price (inclusive)
 * @param maxPrice maximum estimated price (inclusive)
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
public record PriceRange(
        BigDecimal minPrice,
        BigDecimal maxPrice
) {}
