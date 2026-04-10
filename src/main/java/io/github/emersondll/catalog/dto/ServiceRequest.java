package io.github.emersondll.catalog.dto;

import io.github.emersondll.catalog.enumerations.ServiceType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) para requisições de criação e atualização de serviços.
 *
 * <p>Utiliza o padrão Record para representar dados imutáveis de entrada.
 * Contém validações de negócio via Jakarta Bean Validation.</p>
 *
 * @author Emerson DLL
 * @since 1.0.0
 * @see ServiceResponse
 */
public record ServiceRequest(

        @NotBlank(message = "O nome do serviço é obrigatório")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String name,

        @NotBlank(message = "A descrição do serviço é obrigatória")
        @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
        String description,

        @NotNull(message = "O preço base é obrigatório")
        @DecimalMin(value = "0.01", message = "O preço base deve ser maior que zero")
        @Digits(integer = 10, fraction = 2, message = "O preço base deve ter no máximo 10 dígitos inteiros e 2 decimais")
        BigDecimal basePrice,

        @NotNull(message = "A duração em horas é obrigatória")
        @Min(value = 1, message = "A duração deve ser de pelo menos 1 hora")
        @Max(value = 24, message = "A duração não pode exceder 24 horas")
        Integer durationInHours,

        @NotNull(message = "O tipo de serviço é obrigatório")
        ServiceType type
) {}
