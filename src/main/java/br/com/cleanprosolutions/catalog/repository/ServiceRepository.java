package br.com.cleanprosolutions.catalog.repository;

import br.com.cleanprosolutions.catalog.document.ServiceDocument;
import br.com.cleanprosolutions.catalog.enumerations.ServiceCategory;
import br.com.cleanprosolutions.catalog.enumerations.ServiceType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for {@link ServiceDocument} persistence.
 *
 * <p>Extends {@link MongoRepository} with custom finders for catalog filtering.</p>
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
@Repository
public interface ServiceRepository extends MongoRepository<ServiceDocument, String> {

    /**
     * Finds all active services.
     *
     * @return list of active services
     */
    List<ServiceDocument> findByActiveTrue();

    /**
     * Finds all active services by type.
     *
     * @param type the service type filter
     * @return list of active services of the given type
     */
    List<ServiceDocument> findByActiveTrueAndType(ServiceType type);

    /**
     * Finds all active services by category.
     *
     * @param category the service category filter
     * @return list of active services in the given category
     */
    List<ServiceDocument> findByActiveTrueAndCategory(ServiceCategory category);

    /**
     * Full-text search across service name and description using the MongoDB {@code $text} operator.
     *
     * <p>Requires the {@code @TextIndexed} fields to be indexed in MongoDB.
     * The index is created automatically by Spring Data on startup.</p>
     *
     * @param query the search term(s)
     * @return active services matching the text query
     */
    @Query("{ '$text': { '$search': ?0 }, 'active': true }")
    List<ServiceDocument> searchByText(String query);
}
