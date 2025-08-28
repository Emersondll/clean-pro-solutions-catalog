# 🧹 Service Catalog Service

Este microserviço faz parte do **Clean Pro Solutions** e é responsável por gerenciar os **serviços de catálogo** oferecidos (residenciais, comerciais, pós-obra, etc).

Ele expõe uma API REST para **criação, atualização, listagem e exclusão** de serviços no MongoDB.

---


---

## 📌 Tecnologias Utilizadas
- **Java 21**
- **Spring Boot 3.x**
- **Spring Web**
- **Spring Data MongoDB**
- **Docker & Docker Compose**
- **MongoDB**
- **Lombok**
- **Maven**

---
## 📖 Endpoints da API

````

---


### 🔹 Criar Serviço
**POST** `/v1/api/services`

**Request Body**
```json
{
  "name": "Limpeza Residencial",
  "description": "Serviço completo de limpeza em residências",
  "basePrice": 200.0,
  "durationInHours": 4,
  "type": "RESIDENTIAL"
}
````

**Response**

```json
{
  "id": "64cbb1f2a4c1c2b8e2d7f0a9",
  "name": "Limpeza Residencial",
  "description": "Serviço completo de limpeza em residências",
  "basePrice": 200.0,
  "durationInHours": 4,
  "type": "RESIDENTIAL"
}
```

---

### 🔹 Atualizar Serviço

**PUT** `/v1/api/services/{id}`

**Request Body**

```json
{
  "name": "Limpeza Pós-Obra",
  "description": "Limpeza pesada após construção ou reforma",
  "basePrice": 500.0,
  "durationInHours": 8,
  "type": "POST_CONSTRUCTION"
}
```

**Response**

```json
{
  "id": "64cbb1f2a4c1c2b8e2d7f0a9",
  "name": "Limpeza Pós-Obra",
  "description": "Limpeza pesada após construção ou reforma",
  "basePrice": 500.0,
  "durationInHours": 8,
  "type": "POST_CONSTRUCTION"
}
```

---

### 🔹 Buscar Serviço por ID

**GET** `/v1/api/services/{id}`

**Response**

```json
{
  "id": "64cbb1f2a4c1c2b8e2d7f0a9",
  "name": "Limpeza Residencial",
  "description": "Serviço completo de limpeza em residências",
  "basePrice": 200.0,
  "durationInHours": 4,
  "type": "RESIDENTIAL"
}
```

---

### 🔹 Listar Todos os Serviços

**GET** `/v1/api/services`

**Response**

```json
[
  {
    "id": "64cbb1f2a4c1c2b8e2d7f0a9",
    "name": "Limpeza Residencial",
    "description": "Serviço completo de limpeza em residências",
    "basePrice": 200.0,
    "durationInHours": 4,
    "type": "RESIDENTIAL"
  },
  {
    "id": "64cbb23da4c1c2b8e2d7f0b0",
    "name": "Limpeza Comercial",
    "description": "Limpeza recorrente de escritórios e comércios",
    "basePrice": 350.0,
    "durationInHours": 6,
    "type": "COMMERCIAL"
  }
]
```

---

### 🔹 Deletar Serviço

**DELETE** `/v1/api/services/{id}`

**Response**

```
204 No Content
```

---

## ⚙️ Configuração da Aplicação

Arquivo `application.yml`:

```yaml
spring:
  application:
    name: catalog-service

---
spring:
  config:
    activate:
      on-profile: dev
  data:
    mongodb:
      host: localhost
      port: 27017
      database: catalog_service_dev

---
spring:
  config:
    activate:
      on-profile: prod
  data:
    mongodb:
      host: mongo
      port: 27017
      database: catalog_service_prod
```

---

## 🐳 Docker

### docker-compose.yml

```yaml
version: "3.9"

services:
  mongo:
    image: mongo:6.0
    container_name: mongo
    restart: always
    ports:
      - "27017:27017"
    environment:
      MONGO_INITDB_DATABASE: catalogdb
    volumes:
      - mongo_data:/data/db
volumes:
  mongo_data:

```

---

## ✅ Diferencial do Serviço

O `service-catalog-service` permite que a empresa **cadastre e gerencie os serviços de limpeza oferecidos** com informações completas, integrando-se futuramente com:

* **user-service** (para clientes e colaboradores),
* **billing-service** (para faturamento).

---

✍️ Autor: **Emerson DLL**



