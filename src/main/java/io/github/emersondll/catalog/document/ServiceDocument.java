package io.github.emersondll.catalog.document;

import io.github.emersondll.catalog.enumerations.ServiceType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Entidade de documento que representa um serviço no MongoDB.
 *
 * <p>Esta classe utiliza o padrão Rich Domain Model com objetos de valor imutáveis.
 * Implementa {@link Cloneable} para permitir operações de cópia defensiva.</p>
 *
 * <p>Nota: O método {@code equals} considera dois documentos iguais quando o {@code id} é igual.
 * Isso segue o padrão de identidade de entidade onde o ID é o identificador único.</p>
 *
 * @author Emerson DLL
 * @since 1.0.0
 * @see ServiceType
 */
@Document(collection = "services")
public final class ServiceDocument implements Cloneable {

    @Id
    private final String id;

    private final String name;

    private final String description;

    private final BigDecimal basePrice;

    private final Integer durationInHours;

    private final ServiceType type;

    /**
     * Construtor completo para criação de um ServiceDocument.
     *
     * @param id               identificador único do serviço (pode ser null para novos registros)
     * @param name             nome do serviço (não pode ser vazio)
     * @param description      descrição detalhada do serviço
     * @param basePrice        preço base do serviço (deve ser positivo)
     * @param durationInHours  duração estimada em horas (deve ser positiva)
     * @param type             tipo do serviço
     */
    public ServiceDocument(
            final String id,
            final String name,
            final String description,
            final BigDecimal basePrice,
            final Integer durationInHours,
            final ServiceType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.durationInHours = durationInHours;
        this.type = type;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public BigDecimal basePrice() {
        return basePrice;
    }

    public Integer durationInHours() {
        return durationInHours;
    }

    public ServiceType type() {
        return type;
    }

    /**
     * Cria uma cópia defensiva do documento.
     *
     * @return nova instância com os mesmos valores
     */
    public ServiceDocument copy() {
        return new ServiceDocument(id, name, description, basePrice, durationInHours, type);
    }

    @Override
    public ServiceDocument clone() {
        return copy();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        final ServiceDocument that = (ServiceDocument) other;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ServiceDocument{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", basePrice=" + basePrice +
                ", durationInHours=" + durationInHours +
                ", type=" + type +
                '}';
    }

    /**
     * Builder pattern para criação de ServiceDocument.
     *
     * @return nova instância do Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder interno para construção de ServiceDocument.
     */
    public static class Builder {
        private String id;
        private String name;
        private String description;
        private BigDecimal basePrice;
        private Integer durationInHours;
        private ServiceType type;

        private Builder() {
        }

        public Builder id(final String id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder description(final String description) {
            this.description = description;
            return this;
        }

        public Builder basePrice(final BigDecimal basePrice) {
            this.basePrice = basePrice;
            return this;
        }

        public Builder durationInHours(final Integer durationInHours) {
            this.durationInHours = durationInHours;
            return this;
        }

        public Builder type(final ServiceType type) {
            this.type = type;
            return this;
        }

        public ServiceDocument build() {
            return new ServiceDocument(id, name, description, basePrice, durationInHours, type);
        }
    }
}
