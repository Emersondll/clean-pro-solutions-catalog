package br.com.cleanprosolutions.catalog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI / Swagger UI configuration for the catalog service.
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI catalogServiceOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Clean Pro Solutions — Catalog Service API")
                        .description("Service catalog management with dynamic pricing.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Clean Pro Solutions Team")
                                .url("https://cleanprosolutions.com.br"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
