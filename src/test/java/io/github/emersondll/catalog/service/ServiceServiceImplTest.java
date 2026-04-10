package io.github.emersondll.catalog.service;

import io.github.emersondll.catalog.document.ServiceDocument;
import io.github.emersondll.catalog.dto.ServiceRequest;
import io.github.emersondll.catalog.dto.ServiceResponse;
import io.github.emersondll.catalog.enumerations.ServiceType;
import io.github.emersondll.catalog.exception.NotFoundException;
import io.github.emersondll.catalog.repository.ServiceRepository;
import io.github.emersondll.catalog.service.impl.ServiceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para {@link ServiceServiceImpl}.
 *
 * <p>Valida a lógica de negócio do serviço utilizando Mockito
 * para mockar o repositório.</p>
 *
 * @author Emerson DLL
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para ServiceServiceImpl")
class ServiceServiceImplTest {

    @Mock
    private ServiceRepository repository;

    @InjectMocks
    private ServiceServiceImpl service;

    private ServiceRequest serviceRequest;

    private ServiceDocument serviceDocument;

    private static final String SERVICE_ID = "64cbb1f2a4c1c2b8e2d7f0a9";

    @BeforeEach
    void setUp() {
        serviceRequest = new ServiceRequest(
                "Limpeza Residencial",
                "Serviço completo de limpeza em residências",
                new BigDecimal("200.00"),
                4,
                ServiceType.RESIDENTIAL
        );

        serviceDocument = ServiceDocument.builder()
                .id(SERVICE_ID)
                .name("Limpeza Residencial")
                .description("Serviço completo de limpeza em residências")
                .basePrice(new BigDecimal("200.00"))
                .durationInHours(4)
                .type(ServiceType.RESIDENTIAL)
                .build();
    }

    @Nested
    @DisplayName("create")
    class CreateMethod {

        @Test
        @DisplayName("Deve criar serviço com sucesso")
        void create_ComRequestValido_RetornaResponse() {
            when(repository.save(any(ServiceDocument.class))).thenReturn(serviceDocument);

            final ServiceResponse response = service.create(serviceRequest);

            assertThat(response).isNotNull();
            assertThat(response.id()).isEqualTo(SERVICE_ID);
            assertThat(response.name()).isEqualTo("Limpeza Residencial");
            assertThat(response.basePrice()).isEqualByComparingTo(new BigDecimal("200.00"));
            assertThat(response.type()).isEqualTo(ServiceType.RESIDENTIAL);

            final ArgumentCaptor<ServiceDocument> captor = ArgumentCaptor.forClass(ServiceDocument.class);
            verify(repository, times(1)).save(captor.capture());

            final ServiceDocument saved = captor.getValue();
            assertThat(saved.name()).isEqualTo("Limpeza Residencial");
            assertThat(saved.basePrice()).isEqualByComparingTo(new BigDecimal("200.00"));
        }

        @Test
        @DisplayName("Deve salvar documento sem ID quando é criação")
        void create_DeveSalvarComIdNulo() {
            when(repository.save(any(ServiceDocument.class))).thenReturn(serviceDocument);

            service.create(serviceRequest);

            final ArgumentCaptor<ServiceDocument> captor = ArgumentCaptor.forClass(ServiceDocument.class);
            verify(repository).save(captor.capture());

            assertThat(captor.getValue().id()).isNull();
        }
    }

    @Nested
    @DisplayName("findById")
    class FindByIdMethod {

        @Test
        @DisplayName("Deve retornar serviço quando encontrado")
        void findById_ComIdExistente_RetornaResponse() {
            when(repository.findById(SERVICE_ID)).thenReturn(Optional.of(serviceDocument));

            final ServiceResponse response = service.findById(SERVICE_ID);

            assertThat(response).isNotNull();
            assertThat(response.id()).isEqualTo(SERVICE_ID);
            assertThat(response.name()).isEqualTo("Limpeza Residencial");

            verify(repository, times(1)).findById(SERVICE_ID);
        }

        @Test
        @DisplayName("Deve lançar NotFoundException quando não encontrado")
        void findById_ComIdInexistente_LancaExcecao() {
            final String nonExistentId = "nonexistent";
            when(repository.findById(nonExistentId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.findById(nonExistentId))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessageContaining("Serviço não encontrado com ID: " + nonExistentId);

            verify(repository, times(1)).findById(nonExistentId);
        }
    }

    @Nested
    @DisplayName("findAll")
    class FindAllMethod {

        @Test
        @DisplayName("Deve retornar lista de serviços")
        void findAll_ComServicosExistentes_RetornaLista() {
            final ServiceDocument anotherDocument = ServiceDocument.builder()
                    .id("64cbb23da4c1c2b8e2d7f0b0")
                    .name("Limpeza Comercial")
                    .description("Limpeza de escritórios")
                    .basePrice(new BigDecimal("350.00"))
                    .durationInHours(6)
                    .type(ServiceType.COMMERCIAL)
                    .build();

            when(repository.findAll()).thenReturn(List.of(serviceDocument, anotherDocument));

            final List<ServiceResponse> responses = service.findAll();

            assertThat(responses).hasSize(2);
            assertThat(responses.get(0).id()).isEqualTo(SERVICE_ID);
            assertThat(responses.get(1).id()).isEqualTo("64cbb23da4c1c2b8e2d7f0b0");

            verify(repository, times(1)).findAll();
        }

        @Test
        @DisplayName("Deve retornar lista vazia quando não há serviços")
        void findAll_SemServicos_RetornaListaVazia() {
            when(repository.findAll()).thenReturn(List.of());

            final List<ServiceResponse> responses = service.findAll();

            assertThat(responses).isEmpty();
            verify(repository, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("update")
    class UpdateMethod {

        @Test
        @DisplayName("Deve atualizar serviço existente com sucesso")
        void update_ComIdExistente_RetornaResponseAtualizado() {
            final ServiceRequest updateRequest = new ServiceRequest(
                    "Limpeza Pós-Obra",
                    "Limpeza pesada após construção",
                    new BigDecimal("500.00"),
                    8,
                    ServiceType.POST_CONSTRUCTION
            );

            final ServiceDocument updatedDocument = ServiceDocument.builder()
                    .id(SERVICE_ID)
                    .name("Limpeza Pós-Obra")
                    .description("Limpeza pesada após construção")
                    .basePrice(new BigDecimal("500.00"))
                    .durationInHours(8)
                    .type(ServiceType.POST_CONSTRUCTION)
                    .build();

            when(repository.findById(SERVICE_ID)).thenReturn(Optional.of(serviceDocument));
            when(repository.save(any(ServiceDocument.class))).thenReturn(updatedDocument);

            final ServiceResponse response = service.update(SERVICE_ID, updateRequest);

            assertThat(response).isNotNull();
            assertThat(response.name()).isEqualTo("Limpeza Pós-Obra");
            assertThat(response.basePrice()).isEqualByComparingTo(new BigDecimal("500.00"));
            assertThat(response.type()).isEqualTo(ServiceType.POST_CONSTRUCTION);

            verify(repository, times(1)).findById(SERVICE_ID);
            verify(repository, times(1)).save(any(ServiceDocument.class));
        }

        @Test
        @DisplayName("Deve lançar NotFoundException quando atualizar serviço inexistente")
        void update_ComIdInexistente_LancaExcecao() {
            final String nonExistentId = "nonexistent";
            when(repository.findById(nonExistentId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.update(nonExistentId, serviceRequest))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessageContaining("Serviço não encontrado com ID: " + nonExistentId);

            verify(repository, times(1)).findById(nonExistentId);
            verify(repository, never()).save(any());
        }

        @Test
        @DisplayName("Deve preservar ID do documento ao atualizar")
        void update_DevePreservarIdOriginal() {
            when(repository.findById(SERVICE_ID)).thenReturn(Optional.of(serviceDocument));
            when(repository.save(any(ServiceDocument.class))).thenReturn(serviceDocument);

            service.update(SERVICE_ID, serviceRequest);

            final ArgumentCaptor<ServiceDocument> captor = ArgumentCaptor.forClass(ServiceDocument.class);
            verify(repository).save(captor.capture());

            assertThat(captor.getValue().id()).isEqualTo(SERVICE_ID);
        }
    }

    @Nested
    @DisplayName("delete")
    class DeleteMethod {

        @Test
        @DisplayName("Deve deletar serviço quando existe")
        void delete_ComIdExistente_NaoLancaExcecao() {
            when(repository.findById(SERVICE_ID)).thenReturn(Optional.of(serviceDocument));
            doNothing().when(repository).deleteById(SERVICE_ID);

            service.delete(SERVICE_ID);

            verify(repository, times(1)).findById(SERVICE_ID);
            verify(repository, times(1)).deleteById(SERVICE_ID);
        }

        @Test
        @DisplayName("Deve lançar NotFoundException quando deletar serviço inexistente")
        void delete_ComIdInexistente_LancaExcecao() {
            final String nonExistentId = "nonexistent";
            when(repository.findById(nonExistentId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.delete(nonExistentId))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessageContaining("Serviço não encontrado com ID: " + nonExistentId);

            verify(repository, times(1)).findById(nonExistentId);
            verify(repository, never()).deleteById(any());
        }
    }
}
