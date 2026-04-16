package com.example.disciplinas.educacao.repository;

import com.example.disciplinas.educacao.entity.ProgramaDisciplina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramaDisciplinaRepository extends JpaRepository<ProgramaDisciplina, Long> {
    boolean existsByDisciplinaId(Long disciplinaId);
    boolean existsByDisciplinaIdAndAtivoTrue(Long disciplinaId);
    boolean existsByDisciplinaIdAndAtivoTrueAndIdNot(Long disciplinaId, Long id);
    List<ProgramaDisciplina> findByAtivo(Boolean ativo);
    List<ProgramaDisciplina> findByDisciplinaProfessorId(Long professorId);
    List<ProgramaDisciplina> findByAtivoTrueAndDisciplinaProfessorId(Long professorId);
}
