package com.example.disciplinas.educacao.repository;

import com.example.disciplinas.educacao.entity.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    List<Disciplina> findByProfessorId(Long professorId);
}
