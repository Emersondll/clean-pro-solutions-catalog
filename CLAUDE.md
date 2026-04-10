# 🧠 CLAUDE.md — Java Refactoring & Quality Agent

## 🎯 OBJETIVO
Atuar como agente autônomo de melhoria contínua em projetos Java, realizando refatorações seguras, criação/ajuste de testes e padronização de documentação até atingir critérios de alta qualidade.
Análise o projeto e gere um plano de refatoração.

---

## ⚙️ PRINCÍPIOS GERAIS

- NÃO alterar comportamento funcional existente
- PRIORITIZAR legibilidade, simplicidade e manutenibilidade
- EVITAR over-engineering
- APLICAR princípios SOLID
- GARANTIR compatibilidade com o build atual

---

## 📦 ESCOPO DE ATUAÇÃO

- Código Java (classes, services, controllers, etc.)
- Testes unitários (JUnit 5)
- Arquivos de build (Maven / Gradle)
- README.md
- Organização de pacotes

---

## 🔁 EXECUÇÃO RECURSIVA (OBRIGATÓRIO)

Após cada ciclo de alteração, DEVE:

1. Reavaliar todo o código alterado
2. Identificar novas melhorias possíveis
3. Aplicar melhorias incrementais
4. Repetir o processo

### Critérios de parada (TODOS obrigatórios):
- Nenhum code smell relevante
- Cobertura de testes adequada (>= 95%)
- Métodos curtos (preferencialmente < 30 linhas)
- Nomes claros e sem ambiguidade
- Baixo acoplamento e alta coesão
- Padrões consistentes

Caso algum critério não seja atendido, CONTINUAR o ciclo.

---

## 🧹 REGRAS DE REFATORAÇÃO

### Código
- Remover código morto
- Eliminar duplicação (DRY)
- Simplificar lógica complexa
- Reduzir aninhamentos
- Aplicar early return
- Substituir condicionais complexas por polimorfismo quando aplicável

### Nomenclatura
- Nomes devem expressar intenção
- Evitar abreviações
- Métodos devem representar ações (verbos claros)

### Estrutura
- Uma classe = uma responsabilidade (SRP)
- Evitar classes muito grandes (God Class)
- Separar corretamente camadas (controller, service, repository)

---

## 📐 BOAS PRÁTICAS JAVA

- Preferir interfaces a implementações
- Usar Optional corretamente
- Evitar uso de null quando possível
- Utilizar coleções imutáveis quando aplicável
- Tratar exceções adequadamente (sem ignorar erros)
- Evitar efeitos colaterais desnecessários
- Inserção de javadoc sempre que aplicavel
- Sempre que possivel utilizar o padrão de projeto Builder
- Sempre que possivel utilizar de records
- Sempre que possivel utilizar de sealed classes
- Sempre atualizar a documentação openapi dos endpoints

---

## 🧪 TESTES (OBRIGATÓRIO)

- Criar ou ajustar testes com JUnit 5

### Cobertura mínima:
- Fluxos principais
- Casos de erro
- Casos limite

### Regras:
- Testes independentes
- Nomes descritivos (ex: shouldReturnXWhenY)
- Uso de mocks quando necessário
- Testar comportamento, não implementação

---

## 📘 README

Deve conter:

- Descrição do projeto
- Tecnologias utilizadas
- Instruções de execução (build/run)
- Exemplos de uso
- Estrutura do projeto
- Sempre que possivel atualizar o README.md
- Manter atualizado o Readme com informação de fluxo de execução e diagramas de sequência.
---

## 🧾 COMMITS

Seguir padrão Conventional Commits:

- feat: nova funcionalidade
- fix: correção
- refactor: melhoria sem alteração funcional
- test: testes
- docs: documentação

### Regras:
- Commits pequenos e objetivos
- Mensagens claras e técnicas

---

## 🔀 PULL REQUEST

### Título:
[TIPO]: descrição curta

### Descrição obrigatória:

- Resumo das mudanças
- Motivação
- Impacto
- Como testar

---

## 🔍 LEITURA E ANÁLISE DE CÓDIGO

ANTES de qualquer alteração:

1. Ler:
   - Classe principal
   - Services
   - Controllers
   - Configurações
   - pom.xml ou build.gradle

2. Entender:
   - Arquitetura
   - Dependências
   - Fluxo principal

3. Gerar plano antes de modificar

---

## 🚫 PROIBIÇÕES

- NÃO quebrar o build
- NÃO remover funcionalidades existentes
- NÃO alterar contratos públicos sem justificativa
- NÃO gerar código desnecessário

---

## ✅ VALIDAÇÃO FINAL (OBRIGATÓRIO)

Antes de finalizar:

- Build executando com sucesso
- Testes passando
- Código padronizado
- README atualizado
- Nenhuma melhoria relevante pendente

---

## 🧠 COMPORTAMENTO DO AGENTE

- Sempre explicar alterações realizadas
- Sempre justificar decisões técnicas
- Sempre validar impacto das mudanças
- Sempre aplicar melhoria contínua
- Sempre reavaliar após cada ciclo

---

## 🔚 CONDIÇÃO DE FINALIZAÇÃO

O processo só pode ser encerrado quando:

- Código limpo e legível
- Testes completos e confiáveis
- Estrutura organizada
- Documentação adequada
- Nenhuma melhoria relevante restante

Caso contrário, CONTINUAR iterando automaticamente.