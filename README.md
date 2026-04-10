# Clean Pro Solutions - Catalog Service

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-6DB33F?style=flat-square&logo=spring&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-6.0-47A248?style=flat-square&logo=mongodb&logoColor=white)
![License](https://img.shields.io/badge/License-Apache%202.0-blue?style=flat-square)

Microsserviço REST para gerenciamento do catálogo de serviços da **Clean Pro Solutions**.
Responsável por gerenciar os serviços oferecidos (residenciais, comerciais, pós-obra, limpeza profunda).

---

## Índice

1. [Visão Geral](#visão-geral)
2. [Tecnologias](#tecnologias)
3. [Pré-requisitos](#pré-requisitos)
4. [Configuração](#configuração)
5. [Executando a Aplicação](#executando-a-aplicação)
6. [Endpoints da API](#endpoints-da-api)
7. [Exemplos de Requisição](#exemplos-de-requisição)
8. [Padrões de Projeto](#padrões-de-projeto)
9. [Arquitetura](#arquitetura)
10. [Contribuição](#contribuição)
11. [Licença](#licença)

---

## Visão Geral

Este microsserviço expõe uma API REST para operações CRUD (Create, Read, Update, Delete) de serviços, permitindo:

- Cadastro de novos serviços no catálogo
- Atualização de serviços existentes
- Consulta de serviços individuais ou listagem completa
- Remoção de serviços

O serviço é projetado para integrar-se futuramente com:
- **User Service** - gestão de clientes e colaboradores
- **Billing Service** - faturamento de serviços

---

## Tecnologias

| Tecnologia | Versão | Descrição |
|------------|--------|-----------|
| Java | 21 | Linguagem principal |
| Spring Boot | 3.5 | Framework principal |
| Spring Data MongoDB | - | Persistência com banco de documentos |
| Jakarta Validation | 3.0 | Validação de dados |
| Lombok | - | Redução de boilerplate |
| Maven | 3.x | Gerenciamento de dependências |
| Docker | 24.x | Containerização |
| Docker Compose | - | Orquestração de containers |
| MongoDB | 6.0 | Banco de dados |

---

## Pré-requisitos

- **Java 21** ou superior
- **Maven 3.8** ou superior
- **Docker** (opcional, para containerização)
- **MongoDB 6.0** (ou utilizar Docker Compose)

---

## Configuração

### Variáveis de Ambiente

| Variável | Descrição | Padrão |
|----------|-----------|--------|
| `SPRING_PROFILES_ACTIVE` | Perfil do Spring (dev, prod) | `dev` |
| `MONGO_HOST` | Host do MongoDB | `localhost` |
| `MONGO_PORT` | Porta do MongoDB | `27017` |
| `MONGO_DATABASE` | Nome do banco de dados | `catalog_service_dev` |

### Profiles

O projeto suporta dois profiles:

- **`dev`**: Configuração para ambiente de desenvolvimento local
- **`prod`**: Configuração para ambiente de produção com MongoDB Docker

---

## Executando a Aplicação

### Usando Maven

```bash
# Compilar
./mvnw clean package

# Executar (desenvolvimento)
./mvnw spring-boot:run

# Executar com profile específico
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

### Usando Docker

```bash
# Subir todos os serviços (MongoDB + Aplicação)
cd docker
docker-compose up -d

# Ou subir apenas o MongoDB para desenvolvimento local
docker-compose up -d mongodb
```

### Usando JAR

```bash
java -jar target/clean-pro-solutions-catalog-1.0.0.jar
```

### Usando Docker Compose (ambiente completo)

```bash
cd docker
docker-compose up -d
```

Isso sobe automaticamente:
- **MongoDB** na porta 27017
- **Catalog Service** na porta 8080

---

## Endpoints da API

A API está versionada em `/v1/api/services`.

### Tabela de Endpoints

| Método | Endpoint | Descrição | Status Code |
|--------|----------|-----------|-------------|
| `POST` | `/v1/api/services` | Criar novo serviço | 201 Created |
| `PUT` | `/v1/api/services/{id}` | Atualizar serviço | 200 OK |
| `GET` | `/v1/api/services/{id}` | Buscar serviço por ID | 200 OK |
| `GET` | `/v1/api/services` | Listar todos os serviços | 200 OK |
| `DELETE` | `/v1/api/services/{id}` | Remover serviço | 204 No Content |

### Códigos de Erro

| Status Code | Descrição |
|-------------|-----------|
| 400 Bad Request | Erro de validação nos dados enviados |
| 404 Not Found | Recurso não encontrado |
| 500 Internal Server Error | Erro interno do servidor |

---

## Exemplos de Requisição

### Criar Serviço

**Requisição:**
```http
POST /v1/api/services
Content-Type: application/json

{
  "name": "Limpeza Residencial",
  "description": "Serviço completo de limpeza em residências",
  "base_price": 200.00,
  "duration_in_hours": 4,
  "type": "RESIDENTIAL"
}
```

**Resposta (201 Created):**
```json
{
  "id": "64cbb1f2a4c1c2b8e2d7f0a9",
  "name": "Limpeza Residencial",
  "description": "Serviço completo de limpeza em residências",
  "base_price": 200.00,
  "duration_in_hours": 4,
  "type": "RESIDENTIAL"
}
```

### Atualizar Serviço

**Requisição:**
```http
PUT /v1/api/services/64cbb1f2a4c1c2b8e2d7f0a9
Content-Type: application/json

{
  "name": "Limpeza Pós-Obra",
  "description": "Limpeza pesada após construção ou reforma",
  "base_price": 500.00,
  "duration_in_hours": 8,
  "type": "POST_CONSTRUCTION"
}
```

### Buscar Serviço por ID

**Requisição:**
```http
GET /v1/api/services/64cbb1f2a4c1c2b8e2d7f0a9
```

### Listar Todos os Serviços

**Requisição:**
```http
GET /v1/api/services
```

### Deletar Serviço

**Requisição:**
```http
DELETE /v1/api/services/64cbb1f2a4c1c2b8e2d7f0a9
```

**Resposta:** `204 No Content`

---

## Padrões de Projeto

Este projeto adota os seguintes padrões de projeto e princípios:

### SOLID

| Princípio | Aplicação |
|-----------|-----------|
| **S**ingle Responsibility | Cada classe tem responsabilidade única |
| **O**pen/Closed | Entidades abertas para extensão, fechadas para modificação |
| **L**iskov Substitution | Implementações seguem corretamente suas interfaces |
| **I**nterface Segregation | Interfaces coesas e específicas |
| **D**ependency Inversion | Dependência de abstrações, não concretagens |

### Padrões Implementados

- **Controller** - Orquestração de requisições HTTP
- **Service Layer** - Lógica de negócio e coordenação
- **Repository** - Abstração de persistência (Spring Data)
- **DTO (Data Transfer Object)** - Transferência de dados entre camadas
- **Builder** - Construção de objetos complexos
- **Custom Exception** - Tratamento de erros específicos
- **Controller Advice** - Tratamento centralizado de exceções

### Validações

Utiliza **Jakarta Bean Validation** para validação em camadas:
- `@NotBlank` - Campos obrigatórios não vazios
- `@NotNull` - Objetos não nulos
- `@Size` - Limites de tamanho
- `@DecimalMin` / `@Digits` - Validação numérica
- `@Min` / `@Max` - Limites numéricos

---

## Arquitetura

```
src/main/java/io/github/emersondll/catalog/
├── controller/          # Controllers REST
│   └── ServiceController.java
├── dto/                 # Data Transfer Objects
│   ├── ServiceRequest.java
│   └── ServiceResponse.java
├── document/            # Documentos MongoDB
│   └── ServiceDocument.java
├── enumeration/         # Enumerações
│   └── ServiceType.java
├── exception/           # Exceções customizadas
│   ├── GlobalExceptionHandler.java
│   └── NotFoundException.java
├── repository/          # Repositórios Spring Data
│   └── ServiceRepository.java
└── service/             # Camada de serviço
    ├── ServiceService.java
    └── impl/
        └── ServiceServiceImpl.java
```

### Fluxo de Dados

```
HTTP Request
    │
    ▼
Controller
    │
    ▼
Service (lógica de negócio)
    │
    ▼
Repository (persistência)
    │
    ▼
MongoDB
```

---

## Testes

```bash
# Executar todos os testes
./mvnw test

# Executar com cobertura
./mvnw test jacoco:report
```

---

## Contribuição

1. Fork o projeto
2. Crie uma branch (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request

---

## Licença

Este projeto está licenciado sob a licença **Apache 2.0** - veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## Autor

**Emerson DLL**

- GitHub: [@emersondll](https://github.com/emersondll)
- LinkedIn: [Emerson DLL](https://www.linkedin.com/in/emersondll/)

---

*Clean Pro Solutions - Transformando limpeza em excelência.*
