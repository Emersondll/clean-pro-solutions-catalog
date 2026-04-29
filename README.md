# Clean Pro Solutions - Catalog Service 📋

## 🎯 Papel no Ecossistema
O **Catalog Service** gerencia o portfólio de serviços disponíveis na plataforma. Ele é responsável por:
- Definição de tipos de limpeza (Residencial, Comercial, Pós-obra, etc.).
- Gerenciamento de preços base e descritivos.
- Listagem de serviços para clientes durante o processo de agendamento.

## 🚀 Tecnologias
- **Java 21** & **Spring Boot 3.3.4**
- **MongoDB** (Persistência do catálogo)
- **Netflix Eureka** (Service Discovery)

## 🛠️ Como Executar

### 1. Execução Isolada (Individual)
Para rodar este serviço e suas dependências:
```bash
docker-compose up -d --build
```
O serviço estará disponível em `http://localhost:8083`.

### 2. Execução Integrada
Este serviço é orquestrado pelo projeto principal [Clean Pro Platform](../README.md).

---
© 2026 Clean Pro Solutions - Desenvolvido por Emerson.
