# EmentaTech Backend

API Spring Boot do sistema **EmentaTech**, responsável pelo controle acadêmico de IES, escolas, cursos, professores, disciplinas, programas de disciplina e bibliografias.

O projeto foi desenvolvido no modelo monolítico, com autenticação via Spring Security e persistência em PostgreSQL.

## Objetivo

Esta API atende o fluxo principal do sistema acadêmico:

- autenticação de administrador e professor
- gestão de IES
- gestão de escolas
- gestão de cursos
- gestão de professores
- gestão de matrizes
- gestão de disciplinas
- gestão de programas de disciplina
- gestão de bibliografias básicas e complementares
- relatórios acadêmicos

## Stack

- Java 17
- Spring Boot 3.3.5
- Spring Web
- Spring Data JPA
- Spring Security
- Spring Validation
- PostgreSQL
- Maven Wrapper

## Estrutura Geral

```text
src/main/java/com/example/disciplinas/educacao/
  config/
  controller/
  dto/
  entity/
  enums/
  exception/
  repository/
  security/
  service/

src/main/resources/
  application.properties
```

## Regras de Negócio Já Implementadas

- exclusão lógica por inativação em vez de remoção física nos principais cadastros
- uma escola deve estar vinculada a uma IES
- um curso possui no máximo uma matriz ativa
- uma disciplina possui no máximo um programa ativo por vez
- bibliografia digital exige link
- bibliografia física exige posição na estante
- programa incompleto considera ausência de campos obrigatórios ou quantidade insuficiente de bibliografias
- professor inativado também tem o acesso bloqueado

## Configuração

Arquivo principal:

```text
src/main/resources/application.properties
```

Configuração atual:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sistema_escolas
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=create
server.port=8081
app.frontend.url=http://localhost:4200
```

### Banco de dados

Crie o banco antes de subir a aplicação:

```sql
CREATE DATABASE sistema_escolas;
```

## Como Executar

### Windows

```powershell
mvnw.cmd spring-boot:run
```

Se o `JAVA_HOME` não estiver configurado:

```powershell
cmd /c "set JAVA_HOME=C:\Program Files\Java\jdk-17&& mvnw.cmd spring-boot:run"
```

### Linux ou macOS

```bash
./mvnw spring-boot:run
```

### Build

```powershell
cmd /c "set JAVA_HOME=C:\Program Files\Java\jdk-17&& mvnw.cmd -q -DskipTests package"
```

Jar gerado em:

```text
target/educacao-0.0.1-SNAPSHOT.jar
```

## Autenticação

A autenticação é baseada em usuários persistidos no banco.

### Fluxo

1. O cliente chama `POST /auth/login` com `username` e `password`
2. A API valida as credenciais no banco
3. O frontend passa a usar `Basic Auth` nas rotas protegidas

### Endpoints de autenticação

- `POST /auth/login`
- `GET /auth/me`

## Perfis de Acesso

- `ROLE_ADMIN`
- `ROLE_PROFESSOR`

## Credenciais Seedadas

Essas credenciais dependem do `DatabaseSeeder` e da aplicação iniciada com a base recriada.

### Administrador

- usuário: `admin`
- senha: `admin123`

### Professores

- usuário: `osvaldo.melo@ementatech.com`
- senha: `prof123`
- usuário: `joelma.pacheco@ementatech.com`
- senha: `prof123`
- usuário: `carlos.leandro@ementatech.com`
- senha: `prof123`
- usuário: `orivaldo.paranainfa@ementatech.com`
- senha: `prof123`

## Proteção de Origem

A API foi configurada para aceitar o frontend em:

```text
http://localhost:4200
```

Em testes externos, como Postman, inclua:

```text
Origin: http://localhost:4200
```

E envie autenticação compatível com o perfil usado.

## Endpoints Principais

### Admin

- `GET /admin/ies`
- `POST /admin/ies`
- `PUT /admin/ies/{id}`

- `GET /admin/escolas`
- `POST /admin/escolas`
- `PUT /admin/escolas/{id}`
- `PATCH /admin/escolas/{id}/inativar`

- `GET /admin/professores`
- `GET /admin/professores/{id}`
- `POST /admin/professores`
- `PUT /admin/professores/{id}`
- `PATCH /admin/professores/{id}/inativar`
- `PATCH /admin/professores/{id}/ativar`
- `POST /admin/professores/{id}/formacoes`

- `GET /admin/cursos`
- `GET /admin/cursos/{id}`
- `POST /admin/cursos`
- `PUT /admin/cursos/{id}`
- `PATCH /admin/cursos/{id}/inativar`
- `PATCH /admin/cursos/{id}/ativar`

- `GET /admin/matrizes`
- `GET /admin/matrizes/{id}`
- `POST /admin/matrizes`
- `PUT /admin/matrizes/{id}`
- `PATCH /admin/matrizes/{id}/inativar`

- `GET /admin/disciplinas`
- `GET /admin/disciplinas/{id}`
- `POST /admin/disciplinas`
- `PUT /admin/disciplinas/{id}`
- `PATCH /admin/disciplinas/{id}/inativar`

- `GET /admin/programas-disciplinas`
- `GET /admin/programas-disciplinas/{id}`
- `POST /admin/programas-disciplinas`
- `PUT /admin/programas-disciplinas/{id}`
- `PATCH /admin/programas-disciplinas/{id}/inativar`

### Professor

- `GET /professor/me`
- `POST /professor/me/formacoes`
- `GET /professor/programas`
- `GET /professor/programas/incompletos`
- `POST /professor/programas/{programaId}/bibliografias/basicas`
- `POST /professor/programas/{programaId}/bibliografias/complementares`

### Relatórios

- `GET /relatorios/escolas`
- `GET /relatorios/professores`
- `GET /relatorios/cursos-matrizes`
- `GET /relatorios/programas`
- `GET /relatorios/programas/{id}`
- `GET /relatorios/programas/incompletos`

## Seed de Dados

Ao iniciar com a configuração atual, a aplicação recria a base e popula o sistema com:

- usuário administrador
- professores iniciais
- cursos principais
- disciplinas e programas
- bibliografias

Isso acontece porque o projeto está com:

```properties
spring.jpa.hibernate.ddl-auto=create
```

## Observações Importantes

- a porta padrão atual da API é `8081`
- o frontend foi configurado para consumir esta API em `http://localhost:8081`
- como o banco é recriado com `create`, os dados manuais são perdidos a cada reinicialização
- para testar mudanças de endpoint, reinicie a aplicação depois do build

## Arquivos-Chave

- `src/main/resources/application.properties`
- `src/main/java/com/example/disciplinas/educacao/controller/`
- `src/main/java/com/example/disciplinas/educacao/service/`
- `src/main/java/com/example/disciplinas/educacao/security/`
- `src/main/java/com/example/disciplinas/educacao/service/DatabaseSeeder.java`

## Estado Atual

O backend já está integrado com o frontend Angular do projeto e atende o fluxo principal de autenticação, administração acadêmica e portal do professor.

## Licença

Projeto acadêmico para fins educacionais.
