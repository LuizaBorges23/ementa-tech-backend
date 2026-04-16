package com.example.disciplinas.educacao.repository;

import com.example.disciplinas.educacao.entity.Matriz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatrizRepository extends JpaRepository<Matriz, Long> {
    boolean existsByCursoIdAndAtivoTrue(Long cursoId);
    boolean existsByCursoIdAndAtivoTrueAndIdNot(Long cursoId, Long id);
}
