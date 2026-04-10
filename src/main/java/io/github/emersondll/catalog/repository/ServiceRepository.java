package io.github.emersondll.catalog.repository;

import io.github.emersondll.catalog.document.ServiceDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório MongoDB para operações de persistência de serviços.
 *
 * <p>Estende {@link MongoRepository} fornecendo operações CRUD básicas
 * e personalizadas para a coleção de serviços.</p>
 *
 * @author Emerson DLL
 * @since 1.0.0
 * @see ServiceDocument
 */
@Repository
public interface ServiceRepository extends MongoRepository<ServiceDocument, String> {
}
