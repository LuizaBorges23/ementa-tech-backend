package com.example.disciplinas.educacao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_matriz")
public class Matriz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(nullable = false)
    private LocalDate dataCadastro;

    @Column(nullable = false)
    private Boolean ativo = true;

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_id", nullable = false)
    @JsonIgnoreProperties({"matrizes", "disciplinas", "escola", "coordenadorCurso"})
    private Curso curso;

    @ManyToMany
    @JoinTable(
            name = "tb_matriz_disciplina",
            joinColumns = @JoinColumn(name = "matriz_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplina_id")
    )
    @JsonIgnoreProperties({"matrizes", "cursos", "programaDisciplina", "professor", "escola"})
    private List<Disciplina> disciplinas = new ArrayList<>();

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

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
}
