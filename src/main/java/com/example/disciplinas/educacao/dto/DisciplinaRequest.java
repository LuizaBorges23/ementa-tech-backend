package com.example.disciplinas.educacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaRequest {
    @NotBlank
    private String sigla;
    @NotBlank
    private String descricao;
    @NotNull
    private Integer cargaHoraria;
    @NotNull
    private Long escolaId;
    @NotNull
    private List<Long> cursoIds = new ArrayList<>();
    private Long professorId;
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

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Long getEscolaId() {
        return escolaId;
    }

    public void setEscolaId(Long escolaId) {
        this.escolaId = escolaId;
    }

    public List<Long> getCursoIds() {
        return cursoIds;
    }

    public void setCursoIds(List<Long> cursoIds) {
        this.cursoIds = cursoIds;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
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
