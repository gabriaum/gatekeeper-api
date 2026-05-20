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
  - `DB_PASSWORD`
  - `JWT_SECRET`

> Observação: `src/main/resources/application.properties` lê `spring.datasource.password` de `DB_PASSWORD` e o segredo JWT de `JWT_SECRET`.

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

Criação de usuário `ADMIN` no startup:

- Só ocorre quando `ADMIN_CREATE=true`.
- Requer `ADMIN_CPF`, `ADMIN_EMAIL` e `ADMIN_PASSWORD` preenchidos.
- Se `ADMIN_CREATE=true` sem essas variáveis, a aplicação falha no startup com erro de configuração.
- Se já existir um usuário com role `ADMIN`, nada é criado.

Esse comportamento está em `src/main/java/com/gabriaum/gatekeeper/GateKeeperAdminUser.java`.

## Iniciar com Docker (recomendado)

Pré-requisitos:

- Docker instalado (Docker Desktop ou engine + docker-compose)

O repositório já inclui um `Dockerfile` para criar a imagem da aplicação e um `compose.yaml` para executar a aplicação junto com um container MySQL.

1) Usando Docker Compose (recomendado)

No diretório do projeto execute (PowerShell):

```powershell
docker compose -f compose.yaml up --build -d
```

Comandos úteis:

```powershell
# Ver logs em tempo real
docker compose -f compose.yaml logs -f api

# Parar e remover containers, redes e volumes criados pelo compose
docker compose -f compose.yaml down -v
```

O `compose.yaml` já configura um serviço `mysql` (porta 3306) e o serviço `api` ligado a ele. As variáveis de ambiente necessárias para a aplicação (como `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` e `SPRING_DATASOURCE_PASSWORD`) são definidas no `compose.yaml`.

2) Construir a imagem manualmente e executar com `docker run`

Para construir a imagem com o `Dockerfile`:

```powershell
docker build -t gatekeeper:latest .
```

E executar (exemplo mínimo — lembre-se que a aplicação precisa de um MySQL acessível):

```powershell
# Executando apenas a API (pressupõe que exista um DB em mysql:3306 na rede acessível)
docker run --name gatekeeper -p 8080:8080 \
  -e SPRING_DATASOURCE_URL="jdbc:mysql://mysql:3306/gatekeeper?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true" \
  -e SPRING_DATASOURCE_USERNAME=gatekeeper \
  -e SPRING_DATASOURCE_PASSWORD=gatekeeper \
  -d gatekeeper:latest
```

Observações:

- Se usar a abordagem com `docker run`, você precisará prover um container MySQL separado ou apontar a variável `SPRING_DATASOURCE_URL` para um banco existente.
- O `compose.yaml` incluído é a forma mais simples para rodar tudo localmente (API + MySQL) porque já monta um volume para os dados do MySQL e aguarda o serviço do banco via `healthcheck`.


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

