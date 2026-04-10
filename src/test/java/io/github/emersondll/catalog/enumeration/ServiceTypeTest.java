package io.github.emersondll.catalog.enumeration;

import io.github.emersondll.catalog.enumerations.ServiceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes unitários para {@link ServiceType}.
 *
 * @author Emerson DLL
 * @since 1.0.0
 */
@DisplayName("Testes para ServiceType")
class ServiceTypeTest {

    @Nested
    @DisplayName("getDescription")
    class GetDescriptionMethod {

        @Test
        @DisplayName("RESIDENTIAL deve retornar descrição correta")
        void residential_DeveRetornarDescricaoCorreta() {
            assertThat(ServiceType.RESIDENTIAL.getDescription())
                    .isEqualTo("Limpeza Residencial");
        }

        @Test
        @DisplayName("COMMERCIAL deve retornar descrição correta")
        void commercial_DeveRetornarDescricaoCorreta() {
            assertThat(ServiceType.COMMERCIAL.getDescription())
                    .isEqualTo("Limpeza Comercial");
        }

        @Test
        @DisplayName("POST_CONSTRUCTION deve retornar descrição correta")
        void postConstruction_DeveRetornarDescricaoCorreta() {
            assertThat(ServiceType.POST_CONSTRUCTION.getDescription())
                    .isEqualTo("Limpeza Pós-Obra");
        }

        @Test
        @DisplayName("DEEP_CLEANING deve retornar descrição correta")
        void deepCleaning_DeveRetornarDescricaoCorreta() {
            assertThat(ServiceType.DEEP_CLEANING.getDescription())
                    .isEqualTo("Limpeza Profunda");
        }
    }

    @Nested
    @DisplayName("Enum Sources")
    class EnumSources {

        @ParameterizedTest
        @EnumSource(ServiceType.class)
        @DisplayName("Todos os tipos devem ter descrição não nula")
        void todosTipos_DevemTerDescricaoNaoNula(final ServiceType type) {
            assertThat(type.getDescription()).isNotNull();
        }

        @ParameterizedTest
        @EnumSource(ServiceType.class)
        @DisplayName("Todos os tipos devem ter nome não vazio")
        void todosTipos_DevemTerNomeNaoVazio(final ServiceType type) {
            assertThat(type.name()).isNotEmpty();
        }

        @Test
        @DisplayName("Deve conter exatamente 4 tipos de serviço")
        void deveConterExatamenteQuatroTipos() {
            assertThat(ServiceType.values()).hasSize(4);
        }
    }
}
