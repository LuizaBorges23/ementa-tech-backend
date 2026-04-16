package com.example.disciplinas.educacao.dto;

import jakarta.validation.constraints.NotBlank;

public class InstituicaoRequest {
    @NotBlank
    private String nome;
    @NotBlank
    private String endereco;
    @NotBlank
    private String telefone;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
