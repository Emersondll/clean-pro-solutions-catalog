package io.github.emersondll.catalog.dto;

import io.github.emersondll.catalog.dto.ServiceRequest;
import io.github.emersondll.catalog.enumerations.ServiceType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes unitários para {@link ServiceRequest}.
 *
 * <p>Valida as anotações de Jakarta Bean Validation.</p>
 *
 * @author Emerson DLL
 * @since 1.0.0
 */
@DisplayName("Testes para ServiceRequest")
class ServiceRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private ServiceRequest createValidRequest() {
        return new ServiceRequest(
                "Limpeza Residencial",
                "Serviço completo de limpeza em residências",
                new BigDecimal("200.00"),
                4,
                ServiceType.RESIDENTIAL
        );
    }

    @Nested
    @DisplayName("Validações de Name")
    class NameValidation {

        @Test
        @DisplayName("Nome válido não deve gerar violações")
        void nomeValido_NaoDeveGerarViolacoes() {
            final ServiceRequest request = createValidRequest();

            final Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(request);

            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("Nome nulo deve gerar violação")
        void nomeNulo_DeveGerarViolacao() {
            final ServiceRequest request = new ServiceRequest(
                    null,
                    "Descrição",
                    new BigDecimal("100.00"),
                    2,
                    ServiceType.COMMERCIAL
            );

            final Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(request);

            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage()).isEqualTo("O nome do serviço é obrigatório");
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "  ", "\t"})
        @DisplayName("Nome em branco deve gerar violação")
        void nomeEmBranco_DeveGerarViolacao(final String blankName) {
            final ServiceRequest request = new ServiceRequest(
                    blankName,
                    "Descrição",
                    new BigDecimal("100.00"),
                    2,
                    ServiceType.COMMERCIAL
            );

            final Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(request);

            assertThat(violations).isNotEmpty();
        }

        @Test
        @DisplayName("Nome muito curto deve gerar violação")
        void nomeMuitoCurto_DeveGerarViolacao() {
            final ServiceRequest request = new ServiceRequest(
                    "AB",
                    "Descrição",
                    new BigDecimal("100.00"),
                    2,
                    ServiceType.COMMERCIAL
            );

            final Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(request);

            assertThat(violations).isNotEmpty();
            assertThat(violations.iterator().next().getMessage()).contains("3");
        }

        @Test
        @DisplayName("Nome com 3 caracteres deve ser válido")
        void nomeComTresCaracteres_DeveSerValido() {
            final ServiceRequest request = new ServiceRequest(
                    "ABC",
                    "Descrição",
                    new BigDecimal("100.00"),
                    2,
                    ServiceType.COMMERCIAL
            );

            final Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(request);

            assertThat(violations).isEmpty();
        }
    }

    @Nested
    @DisplayName("Validações de basePrice")
    class BasePriceValidation {

        @Test
        @DisplayName("Preço nulo deve gerar violação")
        void precoNulo_DeveGerarViolacao() {
            final ServiceRequest request = new ServiceRequest(
                    "Nome Valido",
                    "Descrição",
                    null,
                    2,
                    ServiceType.COMMERCIAL
            );

            final Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(request);

            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage()).isEqualTo("O preço base é obrigatório");
        }

        @Test
        @DisplayName("Preço zero deve gerar violação")
        void precoZero_DeveGerarViolacao() {
            final ServiceRequest request = new ServiceRequest(
                    "Nome Valido",
                    "Descrição",
                    BigDecimal.ZERO,
                    2,
                    ServiceType.COMMERCIAL
            );

            final Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(request);

            assertThat(violations).isNotEmpty();
        }

        @Test
        @DisplayName("Preço negativo deve gerar violação")
        void precoNegativo_DeveGerarViolacao() {
            final ServiceRequest request = new ServiceRequest(
                    "Nome Valido",
                    "Descrição",
                    new BigDecimal("-10.00"),
                    2,
                    ServiceType.COMMERCIAL
            );

            final Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(request);

            assertThat(violations).isNotEmpty();
        }

        @Test
        @DisplayName("Preço muito decimal deve gerar violação")
        void precoMuitoDecimal_DeveGerarViolacao() {
            final ServiceRequest request = new ServiceRequest(
                    "Nome Valido",
                    "Descrição",
                    new BigDecimal("100.001"),
                    2,
                    ServiceType.COMMERCIAL
            );

            final Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(request);

            assertThat(violations).isNotEmpty();
        }
    }

    @Nested
    @DisplayName("Validações de durationInHours")
    class DurationValidation {

        @Test
        @DisplayName("Duração zero deve gerar violação")
        void duracaoZero_DeveGerarViolacao() {
            final ServiceRequest request = new ServiceRequest(
                    "Nome Valido",
                    "Descrição",
                    new BigDecimal("100.00"),
                    0,
                    ServiceType.COMMERCIAL
            );

            final Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(request);

            assertThat(violations).isNotEmpty();
        }

        @Test
        @DisplayName("Duração negativa deve gerar violação")
        void duracaoNegativa_DeveGerarViolacao() {
            final ServiceRequest request = new ServiceRequest(
                    "Nome Valido",
                    "Descrição",
                    new BigDecimal("100.00"),
                    -1,
                    ServiceType.COMMERCIAL
            );

            final Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(request);

            assertThat(violations).isNotEmpty();
        }

        @Test
        @DisplayName("Duração maior que 24 deve gerar violação")
        void duracaoMaiorQue24_DeveGerarViolacao() {
            final ServiceRequest request = new ServiceRequest(
                    "Nome Valido",
                    "Descrição",
                    new BigDecimal("100.00"),
                    25,
                    ServiceType.COMMERCIAL
            );

            final Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(request);

            assertThat(violations).isNotEmpty();
        }

        @Test
        @DisplayName("Duração válida não deve gerar violações")
        void duracaoValida_NaoDeveGerarViolacoes() {
            final ServiceRequest request = new ServiceRequest(
                    "Nome Valido",
                    "Descrição",
                    new BigDecimal("100.00"),
                    24,
                    ServiceType.COMMERCIAL
            );

            final Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(request);

            assertThat(violations).isEmpty();
        }
    }

    @Nested
    @DisplayName("Validações de type")
    class TypeValidation {

        @Test
        @DisplayName("Type nulo deve gerar violação")
        void typeNulo_DeveGerarViolacao() {
            final ServiceRequest request = new ServiceRequest(
                    "Nome Valido",
                    "Descrição",
                    new BigDecimal("100.00"),
                    2,
                    null
            );

            final Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(request);

            assertThat(violations).hasSize(1);
            assertThat(violations.iterator().next().getMessage()).isEqualTo("O tipo de serviço é obrigatório");
        }
    }

    @Nested
    @DisplayName("Validações combinadas")
    class CombinedValidation {

        @Test
        @DisplayName("Request válido não deve ter violações")
        void requestValido_NaoDeveTerViolacoes() {
            final ServiceRequest request = createValidRequest();

            final Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(request);

            assertThat(violations).isEmpty();
        }

        @Test
        @DisplayName("Múltiplas violações devem ser detectadas")
        void multiplasViolacoes_DevemSerDetectadas() {
            final ServiceRequest request = new ServiceRequest(
                    null,
                    null,
                    null,
                    null,
                    null
            );

            final Set<ConstraintViolation<ServiceRequest>> violations = validator.validate(request);

            assertThat(violations).hasSizeGreaterThanOrEqualTo(4);
        }
    }
}
