# Exemplos de Requisições

## Login
`POST /auth/login`
```json
{
  "username": "admin",
  "password": "admin123"
}
```

## Criar Escola
`POST /admin/escolas`
```json
{
  "nome": "Escola de Teste",
  "coordenador": "Coordenador Exemplo",
  "iesId": 1,
  "ativo": true
}
```

## Criar Professor
`POST /admin/professores`
```json
{
  "matricula": "2026002",
  "nomeCompleto": "Maria da Silva",
  "email": "maria@ucsal.edu.br",
  "telefone": "71911111111",
  "escolaId": 2,
  "ativo": true,
  "username": "maria",
  "password": "maria123"
}
```

## Criar Curso
`POST /admin/cursos`
```json
{
  "sigla": "SI",
  "descricao": "Sistemas de Informação",
  "escolaId": 2,
  "coordenadorCursoId": 1,
  "ativo": true
}
```

## Criar Disciplina
`POST /admin/disciplinas`
```json
{
  "sigla": "SI201",
  "descricao": "Engenharia de Software",
  "cargaHoraria": 60,
  "escolaId": 2,
  "cursoIds": [1],
  "professorId": 1,
  "ativo": true
}
```

## Criar Programa de Disciplina
`POST /admin/programas-disciplinas`
```json
{
  "disciplinaId": 1,
  "semestre": 1,
  "ementa": "Texto da ementa",
  "competenciasHabilidades": "Texto das competências e habilidades",
  "conteudoProgramatico": "Unidade 1, Unidade 2, Unidade 3",
  "metodologia": "Metodologia da disciplina",
  "processoAvaliacao": "Processo de avaliação da disciplina",
  "prerequisitoIds": [],
  "ativo": true
}
```

## Adicionar Bibliografia Básica
`POST /professor/programas/1/bibliografias/basicas`
```json
{
  "titulo": "Engenharia de Software Moderna",
  "autores": "Fulano de Tal",
  "editora": "Editora Exemplo",
  "isbn": "9780000000001",
  "anoPublicacao": 2023,
  "localizacao": "DIGITAL",
  "linkLivro": "https://exemplo.com/livro.pdf"
}
```

## Adicionar Bibliografia Complementar
`POST /professor/programas/1/bibliografias/complementares`
```json
{
  "titulo": "Arquitetura de Software",
  "autores": "Beltrano",
  "editora": "Editora Técnica",
  "isbn": "9780000000002",
  "anoPublicacao": 2022,
  "localizacao": "FISICO",
  "posicaoEstante": "EST-02-PRAT-04"
}
```
