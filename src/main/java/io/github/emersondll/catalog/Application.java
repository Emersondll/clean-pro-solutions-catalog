package io.github.emersondll.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal da aplicação Catalog Service.
 *
 * <p>Este microserviço é responsável por gerenciar o catálogo de serviços
 * da Clean Pro Solutions, expondo uma API REST para operações CRUD.</p>
 *
 * <p><b>Tecnologias utilizadas:</b></p>
 * <ul>
 *   <li>Spring Boot 3.x</li>
 *   <li>Spring Data MongoDB</li>
 *   <li>Jakarta Validation</li>
 *   <li>Lombok</li>
 * </ul>
 *
 * @author Emerson DLL
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringBootApplication
public class Application {

    /**
     * Método principal que inicia a aplicação Spring Boot.
     *
     * @param args argumentos de linha de comando
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
