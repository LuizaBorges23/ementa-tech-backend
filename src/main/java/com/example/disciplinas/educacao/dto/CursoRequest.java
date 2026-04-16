package com.example.disciplinas.educacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class CursoRequest {
    @NotBlank
    private String sigla;
    @NotBlank
    private String descricao;
    @NotNull
    private Long escolaId;
    private Long coordenadorCursoId;
    private LocalDate dataCadastro;
    private Boolean ativo = true;

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getEscolaId() {
        return escolaId;
    }

    public void setEscolaId(Long escolaId) {
        this.escolaId = escolaId;
    }

    public Long getCoordenadorCursoId() {
        return coordenadorCursoId;
    }

    public void setCoordenadorCursoId(Long coordenadorCursoId) {
        this.coordenadorCursoId = coordenadorCursoId;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
