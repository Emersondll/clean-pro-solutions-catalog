package io.github.emersondll.catalog.service;

import io.github.emersondll.catalog.dto.ServiceRequest;
import io.github.emersondll.catalog.dto.ServiceResponse;

import java.util.List;

public interface ServiceService {
    ServiceResponse create(ServiceRequest request);
    ServiceResponse update(String id, ServiceRequest request);
    ServiceResponse findById(String id);
    List<ServiceResponse> findAll();
    void delete(String id);
}
