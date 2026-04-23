package com.example.disciplinas.educacao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_disciplina")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String sigla;

    @Column(nullable = false, length = 180)
    private String descricao;

    @Column(nullable = false)
    private Integer cargaHoraria;

    @Column(nullable = false)
    private LocalDate dataCadastro;

    @Column(nullable = false)
    private Boolean ativo = true;


    @ManyToOne(optional = false)
    @JoinColumn(name = "escola_id", nullable = false)
    @JsonIgnoreProperties({"cursos", "professores", "ies", "disciplinas"})
    private Escola escola;

    @ManyToMany
    @JoinTable(
            name = "tb_disciplina_curso",
            joinColumns = @JoinColumn(name = "disciplina_id"),
            inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    @JsonIgnoreProperties({"disciplinas", "matrizes", "coordenadorCurso", "escola"})
    private List<Curso> cursos = new ArrayList<>();

    @ManyToMany(mappedBy = "disciplinas")
    @JsonIgnore
    @JsonIgnoreProperties({"disciplinas", "curso"})
    private List<Matriz> matrizes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "professor_id")
    @JsonIgnore
    @JsonIgnoreProperties({"escola", "formacoes", "disciplinas", "usuario"})
    private Professor professor;

    @OneToMany(mappedBy = "disciplina")
    @JsonIgnore
    @JsonIgnoreProperties({"disciplina", "prerequisitos", "bibliografiasBasicas", "bibliografiasComplementares"})
    private List<ProgramaDisciplina> programaDisciplina = new ArrayList<>();



    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Integer getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(Integer cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public Escola getEscola() { return escola; }
    public void setEscola(Escola escola) { this.escola = escola; }

    public List<Curso> getCursos() { return cursos; }
    public void setCursos(List<Curso> cursos) { this.cursos = cursos; }

    public List<Matriz> getMatrizes() { return matrizes; }
    public void setMatrizes(List<Matriz> matrizes) { this.matrizes = matrizes; }

    public Professor getProfessor() { return professor; }
    public void setProfessor(Professor professor) { this.professor = professor; }

    public List<ProgramaDisciplina> getProgramaDisciplina() { return programaDisciplina; }
    public void setProgramaDisciplina(List<ProgramaDisciplina> programaDisciplina) { this.programaDisciplina = programaDisciplina; }
}
