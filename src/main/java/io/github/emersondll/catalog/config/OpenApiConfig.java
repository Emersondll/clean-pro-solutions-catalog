package io.github.emersondll.catalog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuração do OpenAPI 3.0 para documentação da API.
 *
 * <p>Esta classe configura o SpringDoc OpenAPI para gerar
 * a documentação interactive do Swagger UI em {@code /swagger-ui.html}.</p>
 *
 * <p><b>Endpoints disponíveis:</b></p>
 * <ul>
 *   <li>Swagger UI: {@code /swagger-ui.html}</li>
 *   <li>OpenAPI JSON: {@code /v3/api-docs}</li>
 *   <li>OpenAPI YAML: {@code /v3/api-docs.yaml}</li>
 * </ul>
 *
 * @author Emerson DLL
 * @since 1.0.0
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configura o documento OpenAPI global.
     *
     * @return configuração do OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        final Contact contact = new Contact();
        contact.setName("Emerson DLL");
        contact.setUrl("https://github.com/emersondll");
        contact.setEmail("emerson@example.com");

        final License license = new License();
        license.setName("Apache 2.0");
        license.setUrl("https://www.apache.org/licenses/LICENSE-2.0");

        final Info info = new Info();
        info.setTitle("Clean Pro Solutions - Catalog Service API");
        info.setVersion("1.0.0");
        info.setDescription("""
                Microsserviço REST para gerenciamento do catálogo de serviços da Clean Pro Solutions.

                ## Visão Geral
                Este serviço é responsável por gerenciar os serviços oferecidos pela empresa,
                incluindo limpeza residencial, comercial, pós-obra e limpeza profunda.

                ## Autenticação
                Esta API não requer autenticação no momento. Em versões futuras, será integrado
                ao sistema de autenticação da Clean Pro Solutions.

                ## Convenções
                - Todas as datas estão no formato ISO 8601
                - Valores monetários estão em BRL (Real Brasileiro)
                - Durações são expressas em horas inteiras
                """);
        info.setContact(contact);
        info.setLicense(license);

        final Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Servidor de desenvolvimento local");

        final Server prodServer = new Server();
        prodServer.setUrl("http://mongo:27017");
        prodServer.setDescription("Servidor de produção");

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer));
    }
}
