package br.com.cleanprosolutions.catalog.controller;

import br.com.cleanprosolutions.catalog.dto.PriceRange;
import br.com.cleanprosolutions.catalog.dto.ServiceRequest;
import br.com.cleanprosolutions.catalog.dto.ServiceResponse;
import br.com.cleanprosolutions.catalog.enumerations.ServiceCategory;
import br.com.cleanprosolutions.catalog.enumerations.ServiceType;
import br.com.cleanprosolutions.catalog.service.ServiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link ServiceController}.
 *
 * @author Clean Pro Solutions Team
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class ServiceControllerTest {

    @Mock
    private ServiceService service;

    @InjectMocks
    private ServiceController controller;

    private ServiceRequest request;
    private ServiceResponse response;

    @BeforeEach
    void setUp() {
        request = new ServiceRequest(
                "Limpeza", "Desc", new BigDecimal("100"), 2,
                ServiceType.RESIDENTIAL, ServiceCategory.CLEANING,
                new PriceRange(new BigDecimal("50"), new BigDecimal("150")));

        response = new ServiceResponse(
                "doc-123", "Limpeza", "Desc", new BigDecimal("100"), 2,
                ServiceType.RESIDENTIAL, ServiceCategory.CLEANING,
                new PriceRange(new BigDecimal("50"), new BigDecimal("150")), true);
    }

    @Test
    @DisplayName("shouldCreateServiceAndReturn201")
    void shouldCreateServiceAndReturn201() {
        when(service.create(any(ServiceRequest.class))).thenReturn(response);

        final ResponseEntity<ServiceResponse> result = controller.create(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
    }

    @Test
    @DisplayName("shouldFindAllServices")
    void shouldFindAllServices() {
        when(service.findAll()).thenReturn(List.of(response));

        final ResponseEntity<List<ServiceResponse>> result = controller.findAll(null, null, null);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).hasSize(1);
    }

    @Test
    @DisplayName("shouldFindServicesByCategory")
    void shouldFindServicesByCategory() {
        when(service.findByCategory(ServiceCategory.CLEANING)).thenReturn(List.of(response));

        final ResponseEntity<List<ServiceResponse>> result = controller.findAll(null, null, ServiceCategory.CLEANING);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).hasSize(1);
    }
}
