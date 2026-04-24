package com.example.disciplinas.educacao.dto;

import java.util.List;

public record ProfessorPortalResponse(
        Long id,
        String matricula,
        String nomeCompleto,
        String email,
        String telefone,
        Boolean ativo,
        EscolaResumo escola,
        List<FormacaoResumo> formacoes
) {
    public record EscolaResumo(
            Long id,
            String nome
    ) {}

    public record FormacaoResumo(
            Long id,
            String categoriaTitulacao,
            String instituicaoConclusao,
            String nomeCurso,
            Integer anoConclusao
    ) {}
}
