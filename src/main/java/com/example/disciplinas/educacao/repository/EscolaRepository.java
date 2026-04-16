package com.example.disciplinas.educacao.repository;

import com.example.disciplinas.educacao.entity.Escola;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EscolaRepository extends JpaRepository<Escola, Long> {
    List<Escola> findByAtivo(Boolean ativo);
}
