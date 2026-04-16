package com.example.disciplinas.educacao.dto;

import com.example.disciplinas.educacao.enums.CategoriaTitulacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FormacaoProfessorRequest {
    @NotNull
    private CategoriaTitulacao categoriaTitulacao;
    @NotBlank
    private String instituicaoConclusao;
    @NotBlank
    private String nomeCurso;
    @NotNull
    private Integer anoConclusao;

    public CategoriaTitulacao getCategoriaTitulacao() {
        return categoriaTitulacao;
    }

    public void setCategoriaTitulacao(CategoriaTitulacao categoriaTitulacao) {
        this.categoriaTitulacao = categoriaTitulacao;
    }

    public String getInstituicaoConclusao() {
        return instituicaoConclusao;
    }

    public void setInstituicaoConclusao(String instituicaoConclusao) {
        this.instituicaoConclusao = instituicaoConclusao;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public Integer getAnoConclusao() {
        return anoConclusao;
    }

    public void setAnoConclusao(Integer anoConclusao) {
        this.anoConclusao = anoConclusao;
    }
}
