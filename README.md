# API BACK - Sistema de Controle do Programa de Disciplinas e Bibliografia

Projeto em **Java + Spring Boot + Spring Security + PostgreSQL**, alinhado ao cenário da atividade TED monolítica.

## O que esta versão cobre
- Cadastro de IES, escolas, professores, cursos, matrizes, disciplinas e programas de disciplina.
- Cadastro de bibliografia básica e complementar pelo professor.
- Regras de negócio principais:
  - escola/professor/curso/matriz/disciplina/programa usam **inativação** em vez de exclusão física;
  - não podem existir **duas matrizes ativas** para o mesmo curso;
  - não podem existir **dois programas ativos** para a mesma disciplina;
  - disciplina precisa estar vinculada a **pelo menos um curso**;
  - programa incompleto = menos de **3 bibliografias básicas** ou menos de **5 complementares**, ou campos textuais obrigatórios ausentes;
  - bibliografia digital exige **link** e bibliografia física exige **posição na estante**.
- Segurança com **Spring Security + banco de dados**.
- Restrição de origem via `app.frontend.url`.

## Tecnologias
- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Security
- PostgreSQL

## Credenciais iniciais
- Admin:
  - usuário: `admin`
  - senha: `admin123`
- Professor:
  - usuário: `professor1`
  - senha: `prof123`

## Banco de dados
Crie o banco:
```sql
CREATE DATABASE sistema_escolas;
```

## Configuração
Arquivo: `src/main/resources/application.properties`

A propriedade abaixo define qual front pode acessar a API:
```properties
app.frontend.url=http://localhost:4200
```

## Como rodar
No Windows:
```bash
mvnw.cmd spring-boot:run
```

No Linux/Mac:
```bash
./mvnw spring-boot:run
```

## Importante para testar no Postman
Como o cenário exige bloqueio de acesso fora do front, a API valida `Origin`/`Referer`.

Então nas requisições do Postman adicione o header:
```text
Origin: http://localhost:4200
```

E use **Basic Auth** com um dos usuários acima.

## Endpoints principais
### Autenticação
- `POST /auth/login`
- `GET /auth/me`

### Admin
- `GET/POST/PUT /admin/ies`
- `GET/POST/PUT/PATCH /admin/escolas`
- `GET/POST/PUT/PATCH /admin/professores`
- `POST /admin/professores/{id}/formacoes`
- `GET/POST/PUT/PATCH /admin/cursos`
- `GET/POST/PUT/PATCH /admin/matrizes`
- `GET/POST/PUT/PATCH /admin/disciplinas`
- `GET/POST/PUT/PATCH /admin/programas-disciplinas`

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

## Observação honesta
Esta versão foi preparada para ficar muito mais aderente ao cenário. Aqui no ambiente eu não consegui baixar dependências Maven da internet para executar o build completo, então a validação final precisa ser feita no seu computador.
