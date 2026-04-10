package io.github.emersondll.catalog.document;

import io.github.emersondll.catalog.document.ServiceDocument;
import io.github.emersondll.catalog.enumerations.ServiceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes unitários para {@link ServiceDocument}.
 *
 * @author Emerson DLL
 * @since 1.0.0
 */
@DisplayName("Testes para ServiceDocument")
class ServiceDocumentTest {

    private static final String SERVICE_ID = "64cbb1f2a4c1c2b8e2d7f0a9";
    private static final String NAME = "Limpeza Residencial";
    private static final String DESCRIPTION = "Serviço completo de limpeza";
    private static final BigDecimal BASE_PRICE = new BigDecimal("200.00");
    private static final Integer DURATION = 4;
    private static final ServiceType TYPE = ServiceType.RESIDENTIAL;

    private ServiceDocument createDocument() {
        return new ServiceDocument(SERVICE_ID, NAME, DESCRIPTION, BASE_PRICE, DURATION, TYPE);
    }

    private ServiceDocument createDocumentWithoutId() {
        return new ServiceDocument(null, NAME, DESCRIPTION, BASE_PRICE, DURATION, TYPE);
    }

    @Nested
    @DisplayName("Construtor e Getters")
    class ConstructorAndGetters {

        @Test
        @DisplayName("Deve criar documento com todos os campos")
        void deveCriarDocumentoComTodosOsCampos() {
            final ServiceDocument document = createDocument();

            assertThat(document.id()).isEqualTo(SERVICE_ID);
            assertThat(document.name()).isEqualTo(NAME);
            assertThat(document.description()).isEqualTo(DESCRIPTION);
            assertThat(document.basePrice()).isEqualByComparingTo(BASE_PRICE);
            assertThat(document.durationInHours()).isEqualTo(DURATION);
            assertThat(document.type()).isEqualTo(TYPE);
        }

        @Test
        @DisplayName("Deve permitir ID nulo para novos documentos")
        void devePermitirIdNulo() {
            final ServiceDocument document = createDocumentWithoutId();

            assertThat(document.id()).isNull();
        }
    }

    @Nested
    @DisplayName("Builder Pattern")
    class BuilderPattern {

        @Test
        @DisplayName("Deve criar documento usando builder")
        void deveCriarDocumentoUsandoBuilder() {
            final ServiceDocument document = ServiceDocument.builder()
                    .id(SERVICE_ID)
                    .name(NAME)
                    .description(DESCRIPTION)
                    .basePrice(BASE_PRICE)
                    .durationInHours(DURATION)
                    .type(TYPE)
                    .build();

            assertThat(document.id()).isEqualTo(SERVICE_ID);
            assertThat(document.name()).isEqualTo(NAME);
            assertThat(document.basePrice()).isEqualByComparingTo(BASE_PRICE);
        }

        @Test
        @DisplayName("Builder deve permitir construção parcial")
        void builderDevePermitirConstrucaoParcial() {
            final ServiceDocument document = ServiceDocument.builder()
                    .name(NAME)
                    .basePrice(BASE_PRICE)
                    .type(TYPE)
                    .build();

            assertThat(document.name()).isEqualTo(NAME);
            assertThat(document.basePrice()).isEqualByComparingTo(BASE_PRICE);
            assertThat(document.id()).isNull();
            assertThat(document.description()).isNull();
        }
    }

    @Nested
    @DisplayName("equals e hashCode")
    class EqualsAndHashCode {

        @Test
        @DisplayName("Documentos com mesmo ID devem ser iguais")
        void documentosComMesmoIdDevemSerIguais() {
            final ServiceDocument doc1 = new ServiceDocument(SERVICE_ID, NAME, DESCRIPTION, BASE_PRICE, DURATION, TYPE);
            final ServiceDocument doc2 = new ServiceDocument(SERVICE_ID, "Outro Nome", "Outra Desc", new BigDecimal("100.00"), 2, ServiceType.COMMERCIAL);

            assertThat(doc1).isEqualTo(doc2);
            assertThat(doc1.hashCode()).isEqualTo(doc2.hashCode());
        }

        @Test
        @DisplayName("Documentos com IDs diferentes não devem ser iguais")
        void documentosComIdsDiferentesNaoDevemSerIguais() {
            final ServiceDocument doc1 = new ServiceDocument(SERVICE_ID, NAME, DESCRIPTION, BASE_PRICE, DURATION, TYPE);
            final ServiceDocument doc2 = new ServiceDocument("outro-id", NAME, DESCRIPTION, BASE_PRICE, DURATION, TYPE);

            assertThat(doc1).isNotEqualTo(doc2);
        }

        @Test
        @DisplayName("Documentos sem ID são iguais pelo equals (id null = null)")
        void documentosSemIdSaoIguaisPeloEquals() {
            final ServiceDocument doc1 = createDocumentWithoutId();
            final ServiceDocument doc2 = createDocumentWithoutId();

            // Por design, equals usa apenas ID - dois null são iguais
            assertThat(doc1).isEqualTo(doc2);
        }

        @Test
        @DisplayName("Documentos sem ID não são a mesma instância")
        void documentosSemIdNaoSaoMesmaInstancia() {
            final ServiceDocument doc1 = createDocumentWithoutId();
            final ServiceDocument doc2 = createDocumentWithoutId();

            // São instâncias diferentes (!= reference comparison)
            assertThat(doc1).isNotSameAs(doc2);
        }
    }

    @Nested
    @DisplayName("toString")
    class ToStringMethod {

        @Test
        @DisplayName("Deve conter todos os campos no toString")
        void deveConterTodosOsCamposNoToString() {
            final ServiceDocument document = createDocument();
            final String result = document.toString();

            assertThat(result).contains(SERVICE_ID);
            assertThat(result).contains(NAME);
            assertThat(result).contains(DESCRIPTION);
            assertThat(result).contains("200.00");
            assertThat(result).contains("RESIDENTIAL");
        }
    }

    @Nested
    @DisplayName("copy e clone")
    class CopyAndClone {

        @Test
        @DisplayName("copy deve criar cópia defensiva")
        void copyDeveCriarCopiaDefensiva() {
            final ServiceDocument original = createDocument();
            final ServiceDocument copy = original.copy();

            assertThat(copy).isNotSameAs(original);
            assertThat(copy).isEqualTo(original);
        }

        @Test
        @DisplayName("clone deve criar cópia defensiva")
        void cloneDeveCriarCopiaDefensiva() {
            final ServiceDocument original = createDocument();
            final ServiceDocument clone = original.clone();

            assertThat(clone).isNotSameAs(original);
            assertThat(clone).isEqualTo(original);
        }
    }
}
