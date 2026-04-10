# Docker

Este diretório contém arquivos para containerização da aplicação.

## Estrutura

```
docker/
├── docker-compose.yml     # Orquestração dos serviços
└── README.md              # Este arquivo
```

## Serviços

### MongoDB
Banco de dados MongoDB 6.0 para persistência do catálogo de serviços.

### Catalog Service
Microsserviço Spring Boot 3.5 com Java 21.

## Quick Start

### Subir todos os serviços

```bash
cd docker
docker-compose up -d
```

### Subir apenas o MongoDB (para desenvolvimento local)

```bash
cd docker
docker-compose up -d mongodb
```

### Parar todos os serviços

```bash
cd docker
docker-compose down
```

### Ver logs

```bash
docker-compose logs -f catalog-service
docker-compose logs -f mongodb
```

### Reconstruir imagem da aplicação

```bash
cd docker
docker-compose build --no-cache catalog-service
```

## Variáveis de Ambiente

| Variável | Descrição | Padrão |
|----------|-----------|--------|
| `SPRING_PROFILES_ACTIVE` | Perfil do Spring | `dev` |
| `MONGO_HOST` | Host do MongoDB | `mongodb` |
| `MONGO_PORT` | Porta do MongoDB | `27017` |
| `MONGO_DATABASE` | Nome do banco | `catalog_service_dev` |
| `SERVER_PORT` | Porta da aplicação | `8080` |

## Endpoints

- **API**: http://localhost:8080/v1/api/services
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **MongoDB**: localhost:27017