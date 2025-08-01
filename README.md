<div align="center">
  <img src="docs/images/logo.jpg" alt="Logotipo Lutani Lib" width="250"/>
</div>

<h1 align="center">API de Gestão de Bibliotecas Lutani Lib</h1>

![Status](https://img.shields.io/badge/status-concluído-brightgreen)
![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green)
![Database](https://img.shields.io/badge/Database-PostgreSQL-blue)

API RESTful completa para um sistema de gestão interno de bibliotecas, construída com foco em boas práticas de arquitetura, segurança e integridade de dados.

---
## 👥 Equipe do Projeto

* **Back-end:** Lucas Catanio ([@lucascatanio](https://github.com/lucascatanio))
* **Requisitos, Design & Front-end:** Luisa André ([@luisaandre](https://github.com/luisaandre))

---
## 🚀 Principais Funcionalidades

-   **CRUDs Completos e Seguros** para Livros, Leitores, Exemplares e Usuários.
-   **Lógica de Negócio Complexa** para Empréstimos, Devoluções e Renovações, com validações de regras (ex: limite de empréstimos, status do leitor, janela de renovação).
-   **Busca Avançada** de livros por título e/ou autor, com uma versão pública para consulta de disponibilidade.
-   **Segurança Robusta** com Spring Security, incluindo autenticação via banco, autorização por Roles (`ADMINISTRADOR`, `BIBLIOTECARIO`) e criptografia de senhas com BCrypt.
-   **Gerenciamento de Banco de Dados Profissional** com **Flyway** para migrações versionadas, garantindo que o esquema do banco evolua junto com a aplicação.
-   **Deleção Lógica (Soft Delete)** e **Auditoria Completa** para rastrear quem criou, alterou e deletou cada registro, preservando a integridade histórica.
-   **Tarefa Agendada (`@Scheduled`)** que roda autonomamente para atualizar o status de empréstimos vencidos.
-   **Validação de Dados de Entrada** com anotações customizadas e **Tratamento de Exceções Global** para respostas de erro padronizadas e profissionais.
-   **Documentação de API Interativa** gerada automaticamente com **Swagger/OpenAPI**.

---
## 🛠️ Tecnologias Utilizadas

-   **Linguagem & Framework Principal**:
    -   Java 17
    -   Spring Boot 3 (Web, Data JPA, Security)
-   **Persistência de Dados**:
    -   PostgreSQL
    -   Hibernate
    -   Flyway
-   **APIs & Documentação**:
    -   RESTful
    -   Padrão DTO
    -   Bean Validation
    -   Swagger / OpenAPI 3
-   **Ferramentas & Build**:
    -   Maven
    -   Git & GitHub
    -   Postman (para testes manuais)

---
## ⚙️ Como Rodar o Projeto

1.  **Pré-requisitos:**
    -   Java 17 (ou superior)
    -   Maven 3.x
    -   PostgreSQL
    -   Git

2.  **Configuração:**
    -   Clone o repositório: `git clone https://github.com/lucascatanio/backend-lutani-lib.git`
    -   Crie um banco de dados no PostgreSQL (ex: `biblioteca_db`).
    -   Na pasta `src/main/resources/`, duplique o arquivo `application-example.yml` e renomeie a cópia para `application.yml`.
    -   Abra o `application.yml` e preencha as informações do seu banco de dados local (`url`, `username` e `password`).

3.  **Execução:**
    -   Abra um terminal na raiz do projeto e execute: `./mvnw spring-boot:run`
    -   A aplicação estará disponível em `http://localhost:8080`.
    -   A documentação da API estará em `http://localhost:8080/swagger-ui.html`.