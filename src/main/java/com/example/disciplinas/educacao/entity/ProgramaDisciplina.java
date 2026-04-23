package com.example.disciplinas.educacao.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_programa_disciplina")
public class ProgramaDisciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer semestre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String ementa;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String competenciasHabilidades;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String conteudoProgramatico;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String metodologia;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String processoAvaliacao;

    @Column(nullable = false)
    private LocalDate dataCadastro;

    @Column(nullable = false)
    private Boolean ativo = true;

    @ManyToOne(optional = false)
    @JoinColumn(name = "disciplina_id", nullable = false)
    @JsonIgnoreProperties({"programaDisciplina", "cursos", "matrizes", "professor", "escola"})
    private Disciplina disciplina;

    @ManyToMany
    @JoinTable(
            name = "tb_programa_prerequisito",
            joinColumns = @JoinColumn(name = "programa_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplina_prerequisito_id")
    )
    @JsonIgnoreProperties({"programaDisciplina", "cursos", "matrizes", "professor", "escola"})
    private List<Disciplina> prerequisitos = new ArrayList<>();

    @OneToMany(mappedBy = "programaDisciplina", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JsonIgnoreProperties({"programaDisciplina"})
    private List<BibliografiaBasica> bibliografiasBasicas = new ArrayList<>();

    @OneToMany(mappedBy = "programaDisciplina", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JsonIgnoreProperties({"programaDisciplina"})
    private List<BibliografiaComplementar> bibliografiasComplementares = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public List<Disciplina> getPrerequisitos() {
        return prerequisitos;
    }

    public void setPrerequisitos(List<Disciplina> prerequisitos) {
        this.prerequisitos = prerequisitos;
    }

    public List<BibliografiaBasica> getBibliografiasBasicas() {
        return bibliografiasBasicas;
    }

    public void setBibliografiasBasicas(List<BibliografiaBasica> bibliografiasBasicas) {
        this.bibliografiasBasicas = bibliografiasBasicas;
    }

    public List<BibliografiaComplementar> getBibliografiasComplementares() {
        return bibliografiasComplementares;
    }

    public void setBibliografiasComplementares(List<BibliografiaComplementar> bibliografiasComplementares) {
        this.bibliografiasComplementares = bibliografiasComplementares;
    }
}
