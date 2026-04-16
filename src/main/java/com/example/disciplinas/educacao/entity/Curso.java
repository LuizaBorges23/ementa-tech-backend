package com.example.disciplinas.educacao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String sigla;

    @Column(nullable = false, length = 180)
    private String descricao;

    @Column(nullable = false)
    private LocalDate dataCadastro;

    @Column(nullable = false)
    private Boolean ativo = true;

    @ManyToOne(optional = false)
    @JoinColumn(name = "escola_id", nullable = false)
    @JsonIgnoreProperties({"cursos", "professores", "ies"})
    private Escola escola;

    @ManyToOne
    @JoinColumn(name = "coordenador_curso_id")
    @JsonIgnoreProperties({"escola", "formacoes", "disciplinas", "usuario"})
    private Professor coordenadorCurso;

    @OneToMany(mappedBy = "curso")
    @JsonIgnoreProperties({"curso", "disciplinas"})
    private List<Matriz> matrizes = new ArrayList<>();

    @ManyToMany(mappedBy = "cursos")
    @JsonIgnoreProperties({"cursos", "matrizes", "programaDisciplina", "professor", "escola"})
    private List<Disciplina> disciplinas = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Escola getEscola() {
        return escola;
    }

    public void setEscola(Escola escola) {
        this.escola = escola;
    }

    public Professor getCoordenadorCurso() {
        return coordenadorCurso;
    }

    public void setCoordenadorCurso(Professor coordenadorCurso) {
        this.coordenadorCurso = coordenadorCurso;
    }

    public List<Matriz> getMatrizes() {
        return matrizes;
    }

    public void setMatrizes(List<Matriz> matrizes) {
        this.matrizes = matrizes;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
}
