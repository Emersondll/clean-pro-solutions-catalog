package br.com.cleanprosolutions.catalog.service.impl;

import br.com.cleanprosolutions.catalog.document.ServiceDocument;
import br.com.cleanprosolutions.catalog.dto.ServiceRequest;
import br.com.cleanprosolutions.catalog.dto.ServiceResponse;
import br.com.cleanprosolutions.catalog.enumerations.ServiceCategory;
import br.com.cleanprosolutions.catalog.enumerations.ServiceType;
import br.com.cleanprosolutions.catalog.exception.ServiceNotFoundException;
import br.com.cleanprosolutions.catalog.repository.ServiceRepository;
import br.com.cleanprosolutions.catalog.service.ServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link ServiceService}.
 *
 * <p>Handles catalog service management following SOLID principles.</p>
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository repository;

    /** {@inheritDoc} */
    @Override
    public ServiceResponse create(final ServiceRequest request) {
        log.info("Creating catalog service — name: {}, type: {}, category: {}",
                request.name(), request.type(), request.category());

        final ServiceDocument document = toDocument(request);
        final ServiceDocument saved = repository.save(document);

        log.info("Catalog service created — id: {}", saved.id());
        return toResponse(saved);
    }

    /** {@inheritDoc} */
    @Override
    public ServiceResponse update(final String id, final ServiceRequest request) {
        log.info("Updating catalog service — id: {}", id);

        final ServiceDocument existing = repository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(id));

        final ServiceDocument updated = buildUpdated(existing, request);
        final ServiceDocument saved = repository.save(updated);

        log.info("Catalog service updated — id: {}", id);
        return toResponse(saved);
    }

    /** {@inheritDoc} */
    @Override
    public ServiceResponse findById(final String id) {
        log.info("Finding catalog service — id: {}", id);
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ServiceNotFoundException(id));
    }

    /** {@inheritDoc} */
    @Override
    public List<ServiceResponse> findAll() {
        log.info("Listing all catalog services");
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    /** {@inheritDoc} */
    @Override
    public List<ServiceResponse> findAllActive() {
        log.info("Listing all active catalog services");
        return repository.findByActiveTrue().stream().map(this::toResponse).toList();
    }

    /** {@inheritDoc} */
    @Override
    public List<ServiceResponse> findByType(final ServiceType type) {
        log.info("Listing active catalog services by type: {}", type);
        return repository.findByActiveTrueAndType(type).stream().map(this::toResponse).toList();
    }

    /** {@inheritDoc} */
    @Override
    public List<ServiceResponse> findByCategory(final ServiceCategory category) {
        log.info("Listing active catalog services by category: {}", category);
        return repository.findByActiveTrueAndCategory(category).stream().map(this::toResponse).toList();
    }

    /** {@inheritDoc} */
    @Override
    public void delete(final String id) {
        log.info("Deleting catalog service — id: {}", id);
        final ServiceDocument existing = repository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(id));
        repository.deleteById(existing.id());
        log.info("Catalog service deleted — id: {}", id);
    }

    // --- Private helpers ---

    private ServiceDocument toDocument(final ServiceRequest request) {
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

    private ServiceDocument buildUpdated(final ServiceDocument existing, final ServiceRequest request) {
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

    private ServiceResponse toResponse(final ServiceDocument doc) {
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
