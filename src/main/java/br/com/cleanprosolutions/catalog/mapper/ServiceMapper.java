package br.com.cleanprosolutions.catalog.mapper;

import br.com.cleanprosolutions.catalog.document.ServiceDocument;
import br.com.cleanprosolutions.catalog.dto.ServiceRequest;
import br.com.cleanprosolutions.catalog.dto.ServiceResponse;
import org.springframework.stereotype.Component;

/**
 * Maps between {@link ServiceRequest}, {@link ServiceDocument}, and {@link ServiceResponse}.
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
@Component
public class ServiceMapper {

    /**
     * Creates a new {@link ServiceDocument} from a request. Active is set to {@code true} by default.
     */
    public ServiceDocument toDocument(final ServiceRequest request) {
        return ServiceDocument.builder()
                .name(request.name())
                .description(request.description())
                .basePrice(request.basePrice())
                .durationInHours(request.durationInHours())
                .type(request.type())
                .category(request.category())
                .estimatedPriceRange(request.estimatedPriceRange())
                .active(true)
                .build();
    }

    /**
     * Creates an updated {@link ServiceDocument} preserving the original {@code id} and {@code active} flag.
     */
    public ServiceDocument toUpdatedDocument(final ServiceDocument existing, final ServiceRequest request) {
        return ServiceDocument.builder()
                .id(existing.id())
                .name(request.name())
                .description(request.description())
                .basePrice(request.basePrice())
                .durationInHours(request.durationInHours())
                .type(request.type())
                .category(request.category())
                .estimatedPriceRange(request.estimatedPriceRange())
                .active(existing.active())
                .build();
    }

    /**
     * Maps a {@link ServiceDocument} to a {@link ServiceResponse}.
     */
    public ServiceResponse toResponse(final ServiceDocument doc) {
        return new ServiceResponse(
                doc.id(),
                doc.name(),
                doc.description(),
                doc.basePrice(),
                doc.durationInHours(),
                doc.type(),
                doc.category(),
                doc.estimatedPriceRange(),
                doc.active()
        );
    }
}
