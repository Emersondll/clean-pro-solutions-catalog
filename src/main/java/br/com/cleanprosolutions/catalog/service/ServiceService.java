package br.com.cleanprosolutions.catalog.service;

import br.com.cleanprosolutions.catalog.dto.ServiceRequest;
import br.com.cleanprosolutions.catalog.dto.ServiceResponse;
import br.com.cleanprosolutions.catalog.enumerations.ServiceCategory;
import br.com.cleanprosolutions.catalog.enumerations.ServiceType;

import java.util.List;

/**
 * Service contract for catalog management operations.
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
public interface ServiceService {

    /**
     * Creates a new service in the catalog.
     *
     * @param request service data
     * @return created service
     */
    ServiceResponse create(ServiceRequest request);

    /**
     * Updates an existing service.
     *
     * @param id      service MongoDB ID
     * @param request updated data
     * @return updated service
     */
    ServiceResponse update(String id, ServiceRequest request);

    /**
     * Retrieves a service by its ID.
     *
     * @param id service MongoDB ID
     * @return the service
     */
    ServiceResponse findById(String id);

    /**
     * Lists all services in the catalog.
     *
     * @return list of all services
     */
    List<ServiceResponse> findAll();

    /**
     * Lists all active services.
     *
     * @return list of active services
     */
    List<ServiceResponse> findAllActive();

    /**
     * Lists active services filtered by type.
     *
     * @param type the service type
     * @return filtered list
     */
    List<ServiceResponse> findByType(ServiceType type);

    /**
     * Lists active services filtered by category.
     *
     * @param category the service category
     * @return filtered list
     */
    List<ServiceResponse> findByCategory(ServiceCategory category);

    /**
     * Removes a service from the catalog.
     *
     * @param id service MongoDB ID
     */
    void delete(String id);
}
