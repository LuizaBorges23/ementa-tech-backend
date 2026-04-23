package com.example.disciplinas.educacao.dto;

import com.example.disciplinas.educacao.enums.LocalizacaoBibliografia;

import java.time.LocalDate;
import java.util.List;

public record ProgramaDisciplinaResponse(
        Long id,
        Integer semestre,
        String ementa,
        String competenciasHabilidades,
        String conteudoProgramatico,
        String metodologia,
        String processoAvaliacao,
        LocalDate dataCadastro,
        Boolean ativo,
        DisciplinaDetalhe disciplina,
        List<DisciplinaReferencia> prerequisitos,
        List<BibliografiaDetalhe> bibliografiasBasicas,
        List<BibliografiaDetalhe> bibliografiasComplementares
) {
    public record DisciplinaDetalhe(
            Long id,
            String sigla,
            String descricao,
            Integer cargaHoraria,
            Boolean ativo,
            EscolaReferencia escola,
            ProfessorReferencia professor,
            List<CursoReferencia> cursos
    ) {}

    public record DisciplinaReferencia(
            Long id,
            String sigla,
            String descricao
    ) {}

    public record EscolaReferencia(
            Long id,
            String nome
    ) {}

    public record ProfessorReferencia(
            Long id,
            String nomeCompleto
    ) {}

    public record CursoReferencia(
            Long id,
            String sigla,
            String descricao
    ) {}

    public record BibliografiaDetalhe(
            Long id,
            String titulo,
            String autores,
            String editora,
            String isbn,
            Integer anoPublicacao,
            LocalizacaoBibliografia localizacao,
            String linkLivro,
            String posicaoEstante
    ) {}
}
