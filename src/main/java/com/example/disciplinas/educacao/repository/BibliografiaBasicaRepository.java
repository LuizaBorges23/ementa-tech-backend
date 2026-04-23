package com.example.disciplinas.educacao.repository;

import com.example.disciplinas.educacao.entity.BibliografiaBasica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BibliografiaBasicaRepository extends JpaRepository<BibliografiaBasica, Long> {
    long countByProgramaDisciplinaId(Long programaDisciplinaId);
    List<BibliografiaBasica> findByProgramaDisciplinaIdOrderByIdAsc(Long programaDisciplinaId);
}
