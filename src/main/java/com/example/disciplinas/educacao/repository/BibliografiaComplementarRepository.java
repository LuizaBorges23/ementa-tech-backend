package com.example.disciplinas.educacao.repository;

import com.example.disciplinas.educacao.entity.BibliografiaComplementar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BibliografiaComplementarRepository extends JpaRepository<BibliografiaComplementar, Long> {
    long countByProgramaDisciplinaId(Long programaDisciplinaId);
}
