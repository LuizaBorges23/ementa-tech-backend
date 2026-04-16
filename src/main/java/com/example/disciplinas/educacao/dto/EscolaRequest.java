package com.example.disciplinas.educacao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EscolaRequest {
    @NotBlank
    private String nome;
    @NotBlank
    private String coordenador;
    @NotNull
    private Long iesId;
    private Boolean ativo = true;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(String coordenador) {
        this.coordenador = coordenador;
    }

    public Long getIesId() {
        return iesId;
    }

    public void setIesId(Long iesId) {
        this.iesId = iesId;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
