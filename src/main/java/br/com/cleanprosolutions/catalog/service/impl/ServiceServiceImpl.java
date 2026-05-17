package br.com.cleanprosolutions.catalog.service.impl;

import br.com.cleanprosolutions.catalog.document.ServiceDocument;
import br.com.cleanprosolutions.catalog.dto.ServiceRequest;
import br.com.cleanprosolutions.catalog.dto.ServiceResponse;
import br.com.cleanprosolutions.catalog.enumerations.ServiceCategory;
import br.com.cleanprosolutions.catalog.enumerations.ServiceType;
import br.com.cleanprosolutions.catalog.exception.ServiceNotFoundException;
import br.com.cleanprosolutions.catalog.mapper.ServiceMapper;
import br.com.cleanprosolutions.catalog.repository.ServiceRepository;
import br.com.cleanprosolutions.catalog.service.ServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link ServiceService}.
 *
 * <p>Handles catalog service management following SOLID principles.
 * Mapping responsibilities are delegated to {@link ServiceMapper}.</p>
 *
 * @author Emerson Lima
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository repository;
    private final ServiceMapper mapper;

    /** {@inheritDoc} */
    @Override
    public ServiceResponse create(final ServiceRequest request) {
        log.info("Creating catalog service — name: {}, type: {}, category: {}",
                request.name(), request.type(), request.category());

        final ServiceDocument saved = repository.save(mapper.toDocument(request));
        log.info("Catalog service created — id: {}", saved.id());
        return mapper.toResponse(saved);
    }

    /** {@inheritDoc} */
    @Override
    public ServiceResponse update(final String id, final ServiceRequest request) {
        log.info("Updating catalog service — id: {}", id);

        final ServiceDocument existing = repository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(id));

        final ServiceDocument saved = repository.save(mapper.toUpdatedDocument(existing, request));
        log.info("Catalog service updated — id: {}", id);
        return mapper.toResponse(saved);
    }

    /** {@inheritDoc} */
    @Override
    public ServiceResponse findById(final String id) {
        log.info("Finding catalog service — id: {}", id);
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ServiceNotFoundException(id));
    }

    /** {@inheritDoc} */
    @Override
    public List<ServiceResponse> findAll() {
        log.info("Listing all catalog services");
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    /** {@inheritDoc} */
    @Override
    public List<ServiceResponse> findAllActive() {
        log.info("Listing all active catalog services");
        return repository.findByActiveTrue().stream().map(mapper::toResponse).toList();
    }

    /** {@inheritDoc} */
    @Override
    public List<ServiceResponse> findByType(final ServiceType type) {
        log.info("Listing active catalog services by type: {}", type);
        return repository.findByActiveTrueAndType(type).stream().map(mapper::toResponse).toList();
    }

    /** {@inheritDoc} */
    @Override
    public List<ServiceResponse> findByCategory(final ServiceCategory category) {
        log.info("Listing active catalog services by category: {}", category);
        return repository.findByActiveTrueAndCategory(category).stream().map(mapper::toResponse).toList();
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

    /** {@inheritDoc} */
    @Override
    public List<ServiceResponse> search(final String query) {
        log.info("Searching catalog services — query: '{}'", query);
        final List<ServiceResponse> results = repository.searchByText(query).stream()
                .map(mapper::toResponse)
                .toList();
        log.info("Search returned {} results for query: '{}'", results.size(), query);
        return results;
    }
}
