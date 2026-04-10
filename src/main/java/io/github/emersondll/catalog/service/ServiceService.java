package io.github.emersondll.catalog.service;

import io.github.emersondll.catalog.dto.ServiceRequest;
import io.github.emersondll.catalog.dto.ServiceResponse;

import java.util.List;

/**
 * Interface de serviço para operações de catálogo de serviços.
 *
 * <p>Segue o princípio Interface Segregation do SOLID, expondo apenas
 * as operações necessárias para a gestão de serviços.</p>
 *
 * @author Emerson DLL
 * @since 1.0.0
 */
public interface ServiceService {

    /**
     * Cria um novo serviço no catálogo.
     *
     * @param request dados do serviço a ser criado
     * @return serviço criado com ID gerado
     */
    ServiceResponse create(final ServiceRequest request);

    /**
     * Atualiza um serviço existente.
     *
     * @param id      identificador único do serviço
     * @param request novos dados do serviço
     * @return serviço atualizado
     * @throws io.github.emersondll.catalog.exception.NotFoundException se o serviço não for encontrado
     */
    ServiceResponse update(final String id, final ServiceRequest request);

    /**
     * Busca um serviço pelo seu identificador.
     *
     * @param id identificador único do serviço
     * @return serviço encontrado
     * @throws io.github.emersondll.catalog.exception.NotFoundException se o serviço não for encontrado
     */
    ServiceResponse findById(final String id);

    /**
     * Lista todos os serviços cadastrados.
     *
     * @return lista de todos os serviços
     */
    List<ServiceResponse> findAll();

    /**
     * Remove um serviço do catálogo.
     *
     * @param id identificador único do serviço
     * @throws io.github.emersondll.catalog.exception.NotFoundException se o serviço não for encontrado
     */
    void delete(final String id);
}
