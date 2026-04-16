package com.example.disciplinas.educacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MatrizRequest {
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;
    @NotNull
    private Long cursoId;
    private LocalDate dataCadastro;
    private Boolean ativo = true;
    private List<Long> disciplinaIds = new ArrayList<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
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

    public List<Long> getDisciplinaIds() {
        return disciplinaIds;
    }

    public void setDisciplinaIds(List<Long> disciplinaIds) {
        this.disciplinaIds = disciplinaIds;
    }
}
