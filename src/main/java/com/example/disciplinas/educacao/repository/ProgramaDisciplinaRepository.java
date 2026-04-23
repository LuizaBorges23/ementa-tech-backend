package com.example.disciplinas.educacao.repository;

import com.example.disciplinas.educacao.entity.ProgramaDisciplina;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgramaDisciplinaRepository extends JpaRepository<ProgramaDisciplina, Long> {
    @Override
    @EntityGraph(attributePaths = {"disciplina", "prerequisitos"})
    List<ProgramaDisciplina> findAll();

    @Override
    @EntityGraph(attributePaths = {"disciplina", "prerequisitos"})
    Optional<ProgramaDisciplina> findById(Long id);

    boolean existsByDisciplinaId(Long disciplinaId);
    boolean existsByDisciplinaIdAndAtivoTrue(Long disciplinaId);
    boolean existsByDisciplinaIdAndAtivoTrueAndIdNot(Long disciplinaId, Long id);
    List<ProgramaDisciplina> findByAtivo(Boolean ativo);
    List<ProgramaDisciplina> findByDisciplinaProfessorId(Long professorId);
    List<ProgramaDisciplina> findByAtivoTrueAndDisciplinaProfessorId(Long professorId);
}
