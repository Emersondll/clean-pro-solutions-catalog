package io.github.emersondll.catalog.service.impl;

import io.github.emersondll.catalog.document.ServiceDocument;
import io.github.emersondll.catalog.dto.ServiceRequest;
import io.github.emersondll.catalog.dto.ServiceResponse;
import io.github.emersondll.catalog.exception.NotFoundException;
import io.github.emersondll.catalog.repository.ServiceRepository;
import io.github.emersondll.catalog.service.ServiceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementação do serviço de catálogo de serviços.
 *
 * <p>Segue o padrão Service Layer do DDD, coordenando operações de negócio
 * e aplicando as regras de validação. Utiliza injeção de dependências via
 * construtor (Constructor Injection) para garantir immutabilidade e testabilidade.</p>
 *
 * <p>Princípios SOLID aplicados:</p>
 * <ul>
 *   <li><b>S</b>ingle Responsibility - responsabilidade única na gestão de serviços</li>
 *   <li><b>O</b>pen/Closed - aberto para extensão, fechado para modificação</li>
 *   <li><b>L</b>iskov Substitution - implementa corretamente a interface</li>
 *   <li><b>I</b>nterface Segregation - interface coesa com operações específicas</li>
 *   <li><b>D</b>ependency Inversion - depende de abstrações (Repository), não de concretagens</li>
 * </ul>
 *
 * @author Emerson DLL
 * @since 1.0.0
 * @see ServiceService
 */
@Service
public class ServiceServiceImpl implements ServiceService {

    private static final String SERVICE_NOT_FOUND_MESSAGE = "Serviço não encontrado com ID: ";

    private final ServiceRepository repository;

    /**
     * Construtor com dependência do repositório.
     *
     * @param repository repositório de serviços (injetado pelo Spring)
     */
    public ServiceServiceImpl(final ServiceRepository repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse create(final ServiceRequest request) {
        final ServiceDocument document = toDocument(request);
        final ServiceDocument savedDocument = repository.save(document);
        return toResponse(savedDocument);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse update(final String serviceId, final ServiceRequest request) {
        final ServiceDocument existingService = repository.findById(serviceId)
                .orElseThrow(() -> new NotFoundException(SERVICE_NOT_FOUND_MESSAGE + serviceId));

        final ServiceDocument updatedService = buildUpdatedService(existingService, request);
        final ServiceDocument savedDocument = repository.save(updatedService);
        return toResponse(savedDocument);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse findById(final String serviceId) {
        return repository.findById(serviceId)
                .map(this::toResponse)
                .orElseThrow(() -> new NotFoundException(SERVICE_NOT_FOUND_MESSAGE + serviceId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ServiceResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final String serviceId) {
        final ServiceDocument existingService = repository.findById(serviceId)
                .orElseThrow(() -> new NotFoundException(SERVICE_NOT_FOUND_MESSAGE + serviceId));
        repository.deleteById(existingService.id());
    }

    /**
     * Converte um DTO de Request para documento de persistência.
     *
     * @param request DTO com dados de entrada
     * @return documento para persistência
     */
    private ServiceDocument toDocument(final ServiceRequest request) {
        return ServiceDocument.builder()
                .name(request.name())
                .description(request.description())
                .basePrice(request.basePrice())
                .durationInHours(request.durationInHours())
                .type(request.type())
                .build();
    }

    /**
     * Converte um documento de persistência para DTO de Response.
     *
     * @param document documento persistedo
     * @return DTO de resposta
     */
    private ServiceResponse toResponse(final ServiceDocument document) {
        return new ServiceResponse(
                document.id(),
                document.name(),
                document.description(),
                document.basePrice(),
                document.durationInHours(),
                document.type()
        );
    }

    /**
     * Constrói um documento atualizado a partir do existente, aplicando os novos valores do request.
     *
     * @param existingService documento existente a ser atualizado
     * @param request         DTO com os novos valores
     * @return novo documento com valores atualizados
     */
    private ServiceDocument buildUpdatedService(
            final ServiceDocument existingService,
            final ServiceRequest request) {
        return ServiceDocument.builder()
                .id(existingService.id())
                .name(request.name())
                .description(request.description())
                .basePrice(request.basePrice())
                .durationInHours(request.durationInHours())
                .type(request.type())
                .build();
    }
}
