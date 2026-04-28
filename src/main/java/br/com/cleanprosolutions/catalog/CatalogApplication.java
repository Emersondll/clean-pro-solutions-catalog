package br.com.cleanprosolutions.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main entry point for the Catalog Service.
 *
 * <p>Manages the service catalog for Clean Pro Solutions,
 * exposing a REST API for CRUD operations and service discovery via Eureka.</p>
 *
 * @author Clean Pro Solutions Team
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CatalogApplication {

    /**
     * Application entry point.
     *
     * @param args command-line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(CatalogApplication.class, args);
    }
}
