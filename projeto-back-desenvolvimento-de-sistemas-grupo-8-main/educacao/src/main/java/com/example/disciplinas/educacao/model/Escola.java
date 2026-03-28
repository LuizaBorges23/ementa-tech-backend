package com.example.disciplinas.educacao.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_escola")
public class Escola {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(nullable = false, length = 100)
private String nome;

@Column(nullable = false, length = 100)
private String coordenador;

@Column(name = "ies_vinculada", nullable = false)
private String iesVinculada;

@Column(nullable = false)
private Boolean ativo = true;

public Escola() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCoordenador() { return coordenador; }
    public void setCoordenador(String coordenador) { this.coordenador = coordenador; }

    public String getIesVinculada() { return iesVinculada; }
    public void setIesVinculada(String iesVinculada) { this.iesVinculada = iesVinculada; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}


