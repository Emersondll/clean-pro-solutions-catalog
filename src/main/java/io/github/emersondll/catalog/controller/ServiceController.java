package io.github.emersondll.catalog.controller;

import io.github.emersondll.catalog.dto.ServiceRequest;
import io.github.emersondll.catalog.dto.ServiceResponse;
import io.github.emersondll.catalog.service.ServiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para operações de catálogo de serviços.
 *
 * <p>Segue o padrão Controller do Spring MVC, expondo endpoints RESTful
 * para criação, atualização, consulta e exclusão de serviços.
 * Utiliza {@link Valid} para disparar validações de Beans.</p>
 *
 * <p>Características principais:</p>
 * <ul>
 *   <li>Versionamento de API via URL (/v1/api/services)</li>
 *   <li>Documentação via JavaDoc e nomenclatura descritiva</li>
 *   <li>Parâmetros finais em todas as assinaturas</li>
 * </ul>
 *
 * @author Emerson DLL
 * @since 1.0.0
 * @see ServiceService
 * @see ServiceRequest
 * @see ServiceResponse
 */
@RestController
@RequestMapping("/v1/api/services")
public class ServiceController {

    private final ServiceService service;

    /**
     * Construtor com injeção de dependência do serviço.
     *
     * @param service serviço de catálogo de serviços
     */
    public ServiceController(final ServiceService service) {
        this.service = service;
    }

    /**
     * Cria um novo serviço no catálogo.
     *
     * @param request dados do serviço a ser criado
     * @return resposta HTTP 201 (Created) com o serviço criado
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceResponse> create(@Valid @RequestBody final ServiceRequest request) {
        final ServiceResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Atualiza um serviço existente.
     *
     * @param id      identificador único do serviço
     * @param request novos dados do serviço
     * @return resposta HTTP 200 (OK) com o serviço atualizado
     */
    @PutMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ServiceResponse> update(
            @PathVariable final String id,
            @Valid @RequestBody final ServiceRequest request) {
        final ServiceResponse response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Busca um serviço pelo seu identificador.
     *
     * @param id identificador único do serviço
     * @return resposta HTTP 200 (OK) com o serviço encontrado
     */
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ServiceResponse> findById(@PathVariable final String id) {
        final ServiceResponse response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Lista todos os serviços cadastrados.
     *
     * @return resposta HTTP 200 (OK) com lista de todos os serviços
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ServiceResponse>> findAll() {
        final List<ServiceResponse> responses = service.findAll();
        return ResponseEntity.ok(responses);
    }

    /**
     * Remove um serviço do catálogo.
     *
     * @param id identificador único do serviço
     * @return resposta HTTP 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
