package br.com.cleanprosolutions.catalog.controller;

import br.com.cleanprosolutions.catalog.dto.ServiceRequest;
import br.com.cleanprosolutions.catalog.dto.ServiceResponse;
import br.com.cleanprosolutions.catalog.enumerations.ServiceCategory;
import br.com.cleanprosolutions.catalog.enumerations.ServiceType;
import br.com.cleanprosolutions.catalog.service.ServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for catalog management operations.
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
@Tag(name = "Services", description = "Catalog service management endpoints")
public class ServiceController {

    private final ServiceService service;

    @PostMapping
    @Operation(summary = "Create a new catalog service")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Service created"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<ServiceResponse> create(@Valid @RequestBody final ServiceRequest request) {
        log.info("POST /services");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing service")
    public ResponseEntity<ServiceResponse> update(
            @PathVariable final String id,
            @Valid @RequestBody final ServiceRequest request) {
        log.info("PUT /services/{}", id);
        return ResponseEntity.ok(service.update(id, request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a service by ID")
    public ResponseEntity<ServiceResponse> findById(@PathVariable final String id) {
        log.info("GET /services/{}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    @Operation(summary = "List all services, optionally filtered by active status, type, or category")
    public ResponseEntity<List<ServiceResponse>> findAll(
            @RequestParam(required = false) final Boolean activeOnly,
            @RequestParam(required = false) final ServiceType type,
            @RequestParam(required = false) final ServiceCategory category) {

        log.info("GET /services — activeOnly: {}, type: {}, category: {}", activeOnly, type, category);

        if (category != null) {
            return ResponseEntity.ok(service.findByCategory(category));
        } else if (type != null) {
            return ResponseEntity.ok(service.findByType(type));
        } else if (Boolean.TRUE.equals(activeOnly)) {
            return ResponseEntity.ok(service.findAllActive());
        }

        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a service from the catalog")
    public ResponseEntity<Void> delete(@PathVariable final String id) {
        log.info("DELETE /services/{}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
