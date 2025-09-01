# 🛍️ E-commerce Backend

## 📌 Sobre o Projeto

Este projeto implementa um **sistema de gerenciamento de pedidos e produtos para um e-commerce**, desenvolvido em **Java 17** com **Spring Boot**, conforme especificado no desafio técnico.

### ✅ Requisitos Atendidos

- Autenticação com JWT (dois perfis: `ADMIN` e `USER`).
- CRUD completo de produtos.
- Gestão de pedidos com regras:
  - Pedido inicia como `PENDING`.
  - Estoque atualizado apenas após pagamento.
  - Cancelamento automático em caso de falta de estoque.
  - Valor total calculado dinamicamente.
- Endpoints de relatórios com consultas SQL otimizadas:
  - Top 5 usuários que mais compraram.
  - Ticket médio por usuário.
  - Valor total vendido no mês.

---

## 🛠 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3** (Web, Security, Data JPA, Validation)
- **MySQL 8**
- **Maven**
- **Lombok**
- **JJWT** (JSON Web Token)
- **Hibernate**

---

## ⚙️ Pré-requisitos

- Java 17+
- Maven 3+
- MySQL 8+
- IDE de sua preferência (recomenda-se IntelliJ)
- Insomnia ou Postman (para testar os endpoints)

---

## ▶️ Como Rodar a Aplicação

### 1. Clonar o Repositório

```bash
git clone https://github.com/seu-repo/ecommerce-api.git
cd ecommerce-api
```

### 2. Criar o Banco de Dados MySQL

```sql
CREATE DATABASE ecommerce CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. Configurar o `application.yml`

Edite o arquivo `src/main/resources/application.yml` com suas credenciais do banco:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ecommerce?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
    username: admin
    password: 123456
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        format_sql: true
        id:
          uuid:
            storage: char
  open-in-view: false
```

### 4. Rodar a Aplicação

```bash
mvn spring-boot:run
```

### 5. Testar os Endpoints

Utilize o Insomnia ou Postman para consumir os endpoints da API. Exemplos:

- `POST /auth/register`
- `POST /auth/login`
- `GET /products`
- `POST /orders`
- `PATCH /orders/{id}/pay`
- `GET /reports/top-users`
- `GET /reports/avg-ticket`
- `GET /reports/total-monthly-sales`

---

## 🗂 Estrutura Base de Diretórios

```
src/
├── main/
│   ├── java/
│   │   └── com/test/ecommerce/
│   │       ├── controller/
│   │       ├── dto/
│   │       ├── entity/
│   │       ├── repository/
│   │       ├── security/
│   │       ├── service/
│   │       └── EcommerceApplication.java
│   └── resources/
│       ├── application.yml
│       └── static/
└── test/
    └── java/
```

---

## 🧪 Testes

Execute os testes com:

```bash
mvn test
```

---

## 💡 Observações

- A aplicação utiliza UUID como identificador único das entidades.
- As regras de negócio de pedidos foram implementadas com base em transações e validações no serviço.
- O cancelamento automático de pedidos sem estoque pode ser estendido com agendamento (Spring Scheduler ou Quartz).
- O projeto segue boas práticas de **Clean Code** e **Domain-Driven Design**.

---

## 🧠 Destaques Java 17

Este projeto utiliza **`record`** — recurso estável do Java 17 — para definição de DTOs imutáveis, reduzindo boilerplate:

```java
public record RegisterRequest(
    @NotBlank String name,
    @Email String email,
    @NotBlank String password,
    Role role
) {}
```

---

## 🐳 Dump do Banco de Dados

Execute o comando abaixo para exportar o banco:

```bash
mysqldump -u admin -p ecommerce > ecommerce_dump.sql
```

> ⚠️ Certifique-se de que o usuário do MySQL tenha permissão `PROCESS`, senão ocorrerá erro.

---

## 🤝 Contribuição

Pull Requests são bem-vindos! Para grandes mudanças, por favor abra uma issue primeiro para discutir a proposta.

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais informações.
