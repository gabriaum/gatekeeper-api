# GateKeeper API

API REST em Spring Boot para fluxo de cadastro com aprovação administrativa e registro de auditoria de entrada/saida.

## Visao geral

O projeto implementa um fluxo em duas etapas:

1. Usuários enviam uma solicitação de cadastro.
2. Um usuário com role `ADMIN` aprova ou rejeita a solicitação.

Quando aprovada, a solicitação e convertida em usuário oficial (`GateUser`).

Tambem há endpoints de auditoria para registrar entrada e saída de usuários autenticados (ou por CPF, no caso de administradores).

## Stack

- Java 21 (toolchain Gradle)
- Spring Boot 4.0.2
- Spring Security (JWT)
- Spring Data JPA
- Spring Web MVC
- Bean Validation
- MySQL
- Lombok
- Gradle Kotlin DSL

## Pre-requisitos

- JDK 21
- MySQL em execucao
- Variáveis de ambiente para conexão com banco:
  - `DB_HOST`
  - `DB_PORT`
  - `DB_NAME`
  - `DB_USER`

> Observação: em `src/main/resources/application.properties`, `spring.datasource.password` esta vazio. Ajuste esse valor conforme seu ambiente.

## Configuração

Arquivo principal de configuração:

- `src/main/resources/application.properties`

Propriedades relevantes:

- `spring.datasource.url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}`
- `spring.datasource.username=${DB_USER}`
- `spring.jpa.hibernate.ddl-auto=update`
- `api.security.token.secret=...`

## Como executar

No Windows (PowerShell):

```powershell
.\gradlew.bat bootRun
```

No Linux/macOS:

```bash
./gradlew bootRun
```

Ao iniciar, se não existir nenhum usuário `ADMIN`, o projeto cria automaticamente um usuário padrão:

- usuário: `admin`
- senha: `admin`

Esse comportamento está em `src/main/java/com/gabriaum/gatekeeper/GateKeeperAdminUser.java`.

## Como rodar testes

No Windows (PowerShell):

```powershell
.\gradlew.bat test
```

No Linux/macOS:

```bash
./gradlew test
```

## Endpoints

| Metodo | Rota | Acesso      |
| --- | --- |-------------|
| POST | `/api/v1/authentication/register` | Público     |
| GET | `/api/v1/authentication` | `ADMIN`     |
| POST | `/api/v1/authentication/login` | Público     |
| POST | `/api/v1/gateuser/request/{id}/approve` | `ADMIN`     |
| POST | `/api/v1/gateuser/request/{id}/fail` | `ADMIN`     |
| POST | `/api/v1/audit/register/entrance/self` | Autenticado |
| POST | `/api/v1/audit/register/entrance/admin` | `ADMIN`     |
| POST | `/api/v1/audit/register/exit/self` | Autenticado |
| POST | `/api/v1/audit/register/exit/admin` | `ADMIN`     |

### Regras de segurança

Em `src/main/java/com/gabriaum/gatekeeper/infra/security/config/SecurityConfig.java`:

- Rotas públicas:
  - `/api/v1/authentication/login`
  - `/api/v1/authentication/register`
- Demais rotas exigem autenticação.
- Algumas rotas tambem exigem role `ADMIN` via `@PreAuthorize`.

## Observações

- O arquivo `HELP.md` contem links gerais da stack Spring/Gradle.
- O projeto usa JPA com `ddl-auto=update`, então o schema pode ser atualizado automaticamente no startup.

