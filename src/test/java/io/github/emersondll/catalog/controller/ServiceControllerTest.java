package io.github.emersondll.catalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.emersondll.catalog.dto.ServiceRequest;
import io.github.emersondll.catalog.dto.ServiceResponse;
import io.github.emersondll.catalog.enumerations.ServiceType;
import io.github.emersondll.catalog.exception.GlobalExceptionHandler;
import io.github.emersondll.catalog.exception.NotFoundException;
import io.github.emersondll.catalog.service.ServiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes unitários para {@link ServiceController}.
 *
 * <p>Valida o comportamento do controller REST utilizando MockMvc
 * com dependências mockadas via Mockito.</p>
 *
 * @author Emerson DLL
 * @since 1.0.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para ServiceController")
class ServiceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ServiceService service;

    @InjectMocks
    private ServiceController controller;

    private ObjectMapper objectMapper;

    private ServiceRequest serviceRequest;

    private ServiceResponse serviceResponse;

    private static final String BASE_URL = "/v1/api/services";
    private static final String SERVICE_ID = "64cbb1f2a4c1c2b8e2d7f0a9";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();

        serviceRequest = new ServiceRequest(
                "Limpeza Residencial",
                "Serviço completo de limpeza em residências",
                new BigDecimal("200.00"),
                4,
                ServiceType.RESIDENTIAL
        );

        serviceResponse = new ServiceResponse(
                SERVICE_ID,
                "Limpeza Residencial",
                "Serviço completo de limpeza em residências",
                new BigDecimal("200.00"),
                4,
                ServiceType.RESIDENTIAL
        );
    }

    @Nested
    @DisplayName("POST /v1/api/services")
    class CreateEndpoint {

        @Test
        @DisplayName("Deve criar serviço e retornar 201 Created")
        void create_ComDadosValidos_Retorna201Created() throws Exception {
            when(service.create(any(ServiceRequest.class))).thenReturn(serviceResponse);

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(serviceRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(SERVICE_ID))
                    .andExpect(jsonPath("$.name").value("Limpeza Residencial"))
                    .andExpect(jsonPath("$.basePrice").value(200.00))
                    .andExpect(jsonPath("$.type").value("RESIDENTIAL"));

            verify(service, times(1)).create(any(ServiceRequest.class));
        }

        @Test
        @DisplayName("Deve retornar 400 Bad Request quando nome é nulo")
        void create_ComNomeNulo_Retorna400BadRequest() throws Exception {
            final ServiceRequest invalidRequest = new ServiceRequest(
                    null,
                    "Descrição válida",
                    new BigDecimal("100.00"),
                    2,
                    ServiceType.COMMERCIAL
            );

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidRequest)))
                    .andExpect(status().isBadRequest());

            verify(service, never()).create(any());
        }
    }

    @Nested
    @DisplayName("GET /v1/api/services")
    class FindAllEndpoint {

        @Test
        @DisplayName("Deve retornar lista de serviços com 200 OK")
        void findAll_ComServicosExistentes_RetornaListaCom200() throws Exception {
            final ServiceResponse anotherService = new ServiceResponse(
                    "64cbb23da4c1c2b8e2d7f0b0",
                    "Limpeza Comercial",
                    "Limpeza de escritórios",
                    new BigDecimal("350.00"),
                    6,
                    ServiceType.COMMERCIAL
            );

            when(service.findAll()).thenReturn(List.of(serviceResponse, anotherService));

            mockMvc.perform(get(BASE_URL)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$[0].id").value(SERVICE_ID))
                    .andExpect(jsonPath("$[1].id").value("64cbb23da4c1c2b8e2d7f0b0"));

            verify(service, times(1)).findAll();
        }

        @Test
        @DisplayName("Deve retornar lista vazia quando não há serviços")
        void findAll_SemServicos_RetornaListaVazia() throws Exception {
            when(service.findAll()).thenReturn(List.of());

            mockMvc.perform(get(BASE_URL)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(0));

            verify(service, times(1)).findAll();
        }
    }

    @Nested
    @DisplayName("GET /v1/api/services/{id}")
    class FindByIdEndpoint {

        @Test
        @DisplayName("Deve retornar serviço quando encontrado")
        void findById_ComIdExistente_RetornaServicoCom200() throws Exception {
            when(service.findById(SERVICE_ID)).thenReturn(serviceResponse);

            mockMvc.perform(get(BASE_URL + "/" + SERVICE_ID)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(SERVICE_ID))
                    .andExpect(jsonPath("$.name").value("Limpeza Residencial"));

            verify(service, times(1)).findById(SERVICE_ID);
        }

        @Test
        @DisplayName("Deve retornar 404 Not Found quando serviço não existe")
        void findById_ComIdInexistente_Retorna404() throws Exception {
            final String nonExistentId = "nonexistent123";
            when(service.findById(nonExistentId))
                    .thenThrow(new NotFoundException("Serviço não encontrado com ID: " + nonExistentId));

            mockMvc.perform(get(BASE_URL + "/" + nonExistentId)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.error").value("Recurso não encontrado"));

            verify(service, times(1)).findById(nonExistentId);
        }
    }

    @Nested
    @DisplayName("PUT /v1/api/services/{id}")
    class UpdateEndpoint {

        @Test
        @DisplayName("Deve atualizar serviço e retornar 200 OK")
        void update_ComDadosValidos_Retorna200OK() throws Exception {
            final ServiceRequest updateRequest = new ServiceRequest(
                    "Limpeza Pós-Obra",
                    "Limpeza pesada após construção",
                    new BigDecimal("500.00"),
                    8,
                    ServiceType.POST_CONSTRUCTION
            );

            final ServiceResponse updatedResponse = new ServiceResponse(
                    SERVICE_ID,
                    "Limpeza Pós-Obra",
                    "Limpeza pesada após construção",
                    new BigDecimal("500.00"),
                    8,
                    ServiceType.POST_CONSTRUCTION
            );

            when(service.update(eq(SERVICE_ID), any(ServiceRequest.class)))
                    .thenReturn(updatedResponse);

            mockMvc.perform(put(BASE_URL + "/" + SERVICE_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Limpeza Pós-Obra"))
                    .andExpect(jsonPath("$.basePrice").value(500.00))
                    .andExpect(jsonPath("$.type").value("POST_CONSTRUCTION"));

            verify(service, times(1)).update(eq(SERVICE_ID), any(ServiceRequest.class));
        }

        @Test
        @DisplayName("Deve retornar 404 Not Found quando atualizar serviço inexistente")
        void update_ComIdInexistente_Retorna404() throws Exception {
            when(service.update(eq(SERVICE_ID), any(ServiceRequest.class)))
                    .thenThrow(new NotFoundException("Serviço não encontrado com ID: " + SERVICE_ID));

            mockMvc.perform(put(BASE_URL + "/" + SERVICE_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(serviceRequest)))
                    .andExpect(status().isNotFound());

            verify(service, times(1)).update(eq(SERVICE_ID), any(ServiceRequest.class));
        }
    }

    @Nested
    @DisplayName("DELETE /v1/api/services/{id}")
    class DeleteEndpoint {

        @Test
        @DisplayName("Deve deletar serviço e retornar 204 No Content")
        void delete_ComIdExistente_Retorna204NoContent() throws Exception {
            doNothing().when(service).delete(SERVICE_ID);

            mockMvc.perform(delete(BASE_URL + "/" + SERVICE_ID))
                    .andExpect(status().isNoContent());

            verify(service, times(1)).delete(SERVICE_ID);
        }

        @Test
        @DisplayName("Deve retornar 404 Not Found quando deletar serviço inexistente")
        void delete_ComIdInexistente_Retorna404() throws Exception {
            final String nonExistentId = "nonexistent123";
            doThrow(new NotFoundException("Serviço não encontrado com ID: " + nonExistentId))
                    .when(service).delete(nonExistentId);

            mockMvc.perform(delete(BASE_URL + "/" + nonExistentId))
                    .andExpect(status().isNotFound());

            verify(service, times(1)).delete(nonExistentId);
        }
    }
}
