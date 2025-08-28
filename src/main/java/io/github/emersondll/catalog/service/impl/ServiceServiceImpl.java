package io.github.emersondll.catalog.service.impl;

import io.github.emersondll.catalog.document.ServiceDocument;
import io.github.emersondll.catalog.dto.ServiceRequest;
import io.github.emersondll.catalog.dto.ServiceResponse;
import io.github.emersondll.catalog.exception.NotFoundException;
import io.github.emersondll.catalog.repository.ServiceRepository;
import io.github.emersondll.catalog.service.ServiceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository repository;

    public ServiceServiceImpl(ServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public ServiceResponse create(ServiceRequest request) {
        ServiceDocument doc = new ServiceDocument(
                null,
                request.name(),
                request.description(),
                request.basePrice(),
                request.durationInHours(),
                request.type()
        );
        ServiceDocument saved = repository.save(doc);
        return toResponse(saved);
    }

    @Override
    public ServiceResponse update(String id, ServiceRequest request) {
        ServiceDocument existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Service not found: " + id));

        ServiceDocument updated = new ServiceDocument(
                existing.id(),
                request.name(),
                request.description(),
                request.basePrice(),
                request.durationInHours(),
                request.type()
        );

        return toResponse(repository.save(updated));
    }

    @Override
    public ServiceResponse findById(String id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("Service not found: " + id));
    }

    @Override
    public List<ServiceResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Service not found: " + id);
        }
        repository.deleteById(id);
    }

    private ServiceResponse toResponse(ServiceDocument doc) {
        return new ServiceResponse(
                doc.id(),
                doc.name(),
                doc.description(),
                doc.basePrice(),
                doc.durationInHours(),
                doc.type()
        );
    }
}
