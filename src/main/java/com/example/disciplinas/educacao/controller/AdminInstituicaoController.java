package com.example.disciplinas.educacao.controller;

import com.example.disciplinas.educacao.dto.InstituicaoRequest;
import com.example.disciplinas.educacao.entity.InstituicaoEnsinoSuperior;
import com.example.disciplinas.educacao.service.InstituicaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/ies")
public class AdminInstituicaoController {

    private final InstituicaoService service;

    public AdminInstituicaoController(InstituicaoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<InstituicaoEnsinoSuperior>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstituicaoEnsinoSuperior> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<InstituicaoEnsinoSuperior> salvar(@Valid @RequestBody InstituicaoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstituicaoEnsinoSuperior> atualizar(@PathVariable Long id, @Valid @RequestBody InstituicaoRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }
}
