package com.example.disciplinas.educacao.dto;

import com.example.disciplinas.educacao.enums.LocalizacaoBibliografia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BibliografiaRequest {
    @NotBlank
    private String titulo;
    @NotBlank
    private String autores;
    @NotBlank
    private String editora;
    @NotBlank
    private String isbn;
    @NotNull
    private Integer anoPublicacao;
    @NotNull
    private LocalizacaoBibliografia localizacao;
    private String linkLivro;
    private String posicaoEstante;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public LocalizacaoBibliografia getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(LocalizacaoBibliografia localizacao) {
        this.localizacao = localizacao;
    }

    public String getLinkLivro() {
        return linkLivro;
    }

    public void setLinkLivro(String linkLivro) {
        this.linkLivro = linkLivro;
    }

    public String getPosicaoEstante() {
        return posicaoEstante;
    }

    public void setPosicaoEstante(String posicaoEstante) {
        this.posicaoEstante = posicaoEstante;
    }
}
