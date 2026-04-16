package com.example.disciplinas.educacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProgramaDisciplinaRequest {
    @NotNull
    private Long disciplinaId;
    @NotNull
    private Integer semestre;
    @NotBlank
    private String ementa;
    @NotBlank
    private String competenciasHabilidades;
    @NotBlank
    private String conteudoProgramatico;
    @NotBlank
    private String metodologia;
    @NotBlank
    private String processoAvaliacao;
    private LocalDate dataCadastro;
    private Boolean ativo = true;
    private List<Long> prerequisitoIds = new ArrayList<>();

    public Long getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(Long disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public String getEmenta() {
        return ementa;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    public String getCompetenciasHabilidades() {
        return competenciasHabilidades;
    }

    public void setCompetenciasHabilidades(String competenciasHabilidades) {
        this.competenciasHabilidades = competenciasHabilidades;
    }

    public String getConteudoProgramatico() {
        return conteudoProgramatico;
    }

    public void setConteudoProgramatico(String conteudoProgramatico) {
        this.conteudoProgramatico = conteudoProgramatico;
    }

    public String getMetodologia() {
        return metodologia;
    }

    public void setMetodologia(String metodologia) {
        this.metodologia = metodologia;
    }

    public String getProcessoAvaliacao() {
        return processoAvaliacao;
    }

    public void setProcessoAvaliacao(String processoAvaliacao) {
        this.processoAvaliacao = processoAvaliacao;
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

    public List<Long> getPrerequisitoIds() {
        return prerequisitoIds;
    }

    public void setPrerequisitoIds(List<Long> prerequisitoIds) {
        this.prerequisitoIds = prerequisitoIds;
    }
}
