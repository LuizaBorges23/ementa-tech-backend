package com.example.disciplinas.educacao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_escola")
public class Escola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 120)
    private String coordenador;

    @Column(nullable = false)
    private Boolean ativo = true;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ies_id", nullable = false)
    @JsonIgnoreProperties({"escolas"})
    private InstituicaoEnsinoSuperior ies;

    @OneToMany(mappedBy = "escola")
    @JsonIgnoreProperties({"escola", "matrizes", "coordenadorCurso", "disciplinas"})
    private List<Curso> cursos = new ArrayList<>();

    @OneToMany(mappedBy = "escola")
    @JsonIgnoreProperties({"escola", "formacoes", "usuario", "disciplinas"})
    private List<Professor> professores = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public InstituicaoEnsinoSuperior getIes() {
        return ies;
    }

    public void setIes(InstituicaoEnsinoSuperior ies) {
        this.ies = ies;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public List<Professor> getProfessores() {
        return professores;
    }

    public void setProfessores(List<Professor> professores) {
        this.professores = professores;
    }
}
