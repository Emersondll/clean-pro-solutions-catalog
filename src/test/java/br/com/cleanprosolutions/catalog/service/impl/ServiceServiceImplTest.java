package br.com.cleanprosolutions.catalog.service.impl;

import br.com.cleanprosolutions.catalog.document.ServiceDocument;
import br.com.cleanprosolutions.catalog.dto.PriceRange;
import br.com.cleanprosolutions.catalog.dto.ServiceRequest;
import br.com.cleanprosolutions.catalog.dto.ServiceResponse;
import br.com.cleanprosolutions.catalog.enumerations.ServiceCategory;
import br.com.cleanprosolutions.catalog.enumerations.ServiceType;
import br.com.cleanprosolutions.catalog.exception.ServiceNotFoundException;
import br.com.cleanprosolutions.catalog.repository.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link ServiceServiceImpl}.
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class ServiceServiceImplTest {

    @Mock
    private ServiceRepository repository;

    @InjectMocks
    private ServiceServiceImpl service;

    private ServiceRequest request;
    private ServiceDocument document;

    @BeforeEach
    void setUp() {
        request = new ServiceRequest(
                "Limpeza Geral", "Limpeza completa", new BigDecimal("150.00"), 4,
                ServiceType.RESIDENTIAL, ServiceCategory.CLEANING,
                new PriceRange(new BigDecimal("100.00"), new BigDecimal("200.00")));

        document = ServiceDocument.builder()
                .id("doc-123")
                .name("Limpeza Geral")
                .description("Limpeza completa")
                .basePrice(new BigDecimal("150.00"))
                .durationInHours(4)
                .type(ServiceType.RESIDENTIAL)
                .category(ServiceCategory.CLEANING)
                .estimatedPriceRange(new PriceRange(new BigDecimal("100.00"), new BigDecimal("200.00")))
                .active(true)
                .build();
    }

    @Test
    @DisplayName("shouldCreateService")
    void shouldCreateService() {
        when(repository.save(any(ServiceDocument.class))).thenReturn(document);

        final ServiceResponse result = service.create(request);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo("doc-123");
        assertThat(result.name()).isEqualTo("Limpeza Geral");
        assertThat(result.category()).isEqualTo(ServiceCategory.CLEANING);
        verify(repository).save(any(ServiceDocument.class));
    }

    @Test
    @DisplayName("shouldUpdateService")
    void shouldUpdateService() {
        when(repository.findById("doc-123")).thenReturn(Optional.of(document));
        when(repository.save(any(ServiceDocument.class))).thenReturn(document);

        final ServiceResponse result = service.update("doc-123", request);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo("doc-123");
        verify(repository).findById("doc-123");
        verify(repository).save(any(ServiceDocument.class));
    }

    @Test
    @DisplayName("shouldThrowExceptionWhenUpdatingNonExistentService")
    void shouldThrowExceptionWhenUpdatingNonExistentService() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update("doc-123", request))
                .isInstanceOf(ServiceNotFoundException.class);
    }

    @Test
    @DisplayName("shouldFindServiceById")
    void shouldFindServiceById() {
        when(repository.findById("doc-123")).thenReturn(Optional.of(document));

        final ServiceResponse result = service.findById("doc-123");

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo("doc-123");
    }

    @Test
    @DisplayName("shouldFindAllServices")
    void shouldFindAllServices() {
        when(repository.findAll()).thenReturn(List.of(document));

        final List<ServiceResponse> result = service.findAll();

        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("shouldFindActiveServices")
    void shouldFindActiveServices() {
        when(repository.findByActiveTrue()).thenReturn(List.of(document));

        final List<ServiceResponse> result = service.findAllActive();

        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("shouldFindServicesByType")
    void shouldFindServicesByType() {
        when(repository.findByActiveTrueAndType(ServiceType.RESIDENTIAL)).thenReturn(List.of(document));

        final List<ServiceResponse> result = service.findByType(ServiceType.RESIDENTIAL);

        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("shouldFindServicesByCategory")
    void shouldFindServicesByCategory() {
        when(repository.findByActiveTrueAndCategory(ServiceCategory.CLEANING)).thenReturn(List.of(document));

        final List<ServiceResponse> result = service.findByCategory(ServiceCategory.CLEANING);

        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("shouldDeleteService")
    void shouldDeleteService() {
        when(repository.findById("doc-123")).thenReturn(Optional.of(document));

        service.delete("doc-123");

        verify(repository).deleteById("doc-123");
    }
}
