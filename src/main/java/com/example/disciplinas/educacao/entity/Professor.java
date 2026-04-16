package com.example.disciplinas.educacao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String matricula;

    @Column(nullable = false, length = 150)
    private String nomeCompleto;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false, length = 30)
    private String telefone;

    @Column(nullable = false)
    private Boolean ativo = true;

    @ManyToOne(optional = false)
    @JoinColumn(name = "escola_id", nullable = false)
    @JsonIgnoreProperties({"professores", "cursos", "ies"})
    private Escola escola;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"professor"})
    private List<FormacaoProfessor> formacoes = new ArrayList<>();

    @OneToMany(mappedBy = "professor")
    @JsonIgnoreProperties({"professor", "cursos", "matrizes", "programaDisciplina", "escola"})
    private List<Disciplina> disciplinas = new ArrayList<>();

    @OneToOne(mappedBy = "professor")
    @JsonIgnoreProperties({"professor"})
    private Usuario usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public List<FormacaoProfessor> getFormacoes() {
        return formacoes;
    }

    public void setFormacoes(List<FormacaoProfessor> formacoes) {
        this.formacoes = formacoes;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
