package com.example.disciplinas.educacao.repository;

import com.example.disciplinas.educacao.entity.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    @Override
    @EntityGraph(attributePaths = {"cursos", "escola"})
    List<Disciplina> findAll();

    @Override
    @EntityGraph(attributePaths = {"cursos", "escola"})
    Optional<Disciplina> findById(Long id);

    List<Disciplina> findByProfessorId(Long professorId);
}
