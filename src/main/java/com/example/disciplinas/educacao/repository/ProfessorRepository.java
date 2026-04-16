package com.example.disciplinas.educacao.repository;

import com.example.disciplinas.educacao.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    List<Professor> findByAtivo(Boolean ativo);
    Optional<Professor> findByEmail(String email);
    boolean existsByMatricula(String matricula);
}
