package io.github.emersondll.catalog.repository;

import io.github.emersondll.catalog.document.ServiceDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServiceRepository extends MongoRepository<ServiceDocument, String> {
}
