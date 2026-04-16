package com.example.disciplinas.educacao.repository;

import com.example.disciplinas.educacao.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
