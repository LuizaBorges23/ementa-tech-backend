# Ajustes feitos com base no cenário oficial

## Modelagem adicionada
- IES como entidade própria
- Usuário/autenticação em banco
- Formação do professor
- Programa de disciplina com pré-requisitos
- Bibliografia básica e complementar com localização digital/física

## Regras de negócio implementadas
- Inativação no lugar de exclusão para cadastros principais
- Uma única matriz ativa por curso
- Um único programa ativo por disciplina
- Disciplina vinculada a pelo menos um curso
- Disciplina nasce com um programa inicial inativo para não ficar sem associação
- Limite de 3 bibliografias básicas e 5 complementares por programa
- Link obrigatório para bibliografia digital
- Posição na estante obrigatória para bibliografia física

## Segurança
- Spring Security com usuários em banco
- Perfis ADMIN e PROFESSOR
- Restrição por origem do front configurado em `app.frontend.url`
