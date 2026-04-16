package com.example.disciplinas.educacao.entity;

import com.example.disciplinas.educacao.enums.LocalizacaoBibliografia;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_bibliografia_complementar")
public class BibliografiaComplementar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 180)
    private String titulo;

    @Column(nullable = false, length = 180)
    private String autores;

    @Column(nullable = false, length = 120)
    private String editora;

    @Column(nullable = false, length = 30)
    private String isbn;

    @Column(nullable = false)
    private Integer anoPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private LocalizacaoBibliografia localizacao;

    @Column(length = 255)
    private String linkLivro;

    @Column(length = 80)
    private String posicaoEstante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "programa_disciplina_id", nullable = false)
    @JsonIgnoreProperties({"bibliografiasBasicas", "bibliografiasComplementares", "prerequisitos", "disciplina"})
    private ProgramaDisciplina programaDisciplina;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public ProgramaDisciplina getProgramaDisciplina() {
        return programaDisciplina;
    }

    public void setProgramaDisciplina(ProgramaDisciplina programaDisciplina) {
        this.programaDisciplina = programaDisciplina;
    }
}
