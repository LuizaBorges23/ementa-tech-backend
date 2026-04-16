package com.example.disciplinas.educacao.entity;

import com.example.disciplinas.educacao.enums.CategoriaTitulacao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_formacao_professor")
public class FormacaoProfessor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CategoriaTitulacao categoriaTitulacao;

    @Column(nullable = false, length = 150)
    private String instituicaoConclusao;

    @Column(nullable = false, length = 150)
    private String nomeCurso;

    @Column(nullable = false)
    private Integer anoConclusao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "professor_id", nullable = false)
    @JsonIgnoreProperties({"formacoes", "escola", "disciplinas", "usuario"})
    private Professor professor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoriaTitulacao getCategoriaTitulacao() {
        return categoriaTitulacao;
    }

    public void setCategoriaTitulacao(CategoriaTitulacao categoriaTitulacao) {
        this.categoriaTitulacao = categoriaTitulacao;
    }

    public String getInstituicaoConclusao() {
        return instituicaoConclusao;
    }

    public void setInstituicaoConclusao(String instituicaoConclusao) {
        this.instituicaoConclusao = instituicaoConclusao;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public Integer getAnoConclusao() {
        return anoConclusao;
    }

    public void setAnoConclusao(Integer anoConclusao) {
        this.anoConclusao = anoConclusao;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
