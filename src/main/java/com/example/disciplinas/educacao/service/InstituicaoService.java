package com.example.disciplinas.educacao.service;

import com.example.disciplinas.educacao.dto.InstituicaoRequest;
import com.example.disciplinas.educacao.entity.InstituicaoEnsinoSuperior;
import com.example.disciplinas.educacao.exception.ResourceNotFoundException;
import com.example.disciplinas.educacao.repository.InstituicaoEnsinoSuperiorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituicaoService {

    private final InstituicaoEnsinoSuperiorRepository repository;

    public InstituicaoService(InstituicaoEnsinoSuperiorRepository repository) {
        this.repository = repository;
    }

    public List<InstituicaoEnsinoSuperior> listar() {
        return repository.findAll();
    }

    public InstituicaoEnsinoSuperior buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("IES não encontrada com id " + id));
    }

    public InstituicaoEnsinoSuperior salvar(InstituicaoRequest request) {
        InstituicaoEnsinoSuperior ies = new InstituicaoEnsinoSuperior();
        ies.setNome(request.getNome());
        ies.setEndereco(request.getEndereco());
        ies.setTelefone(request.getTelefone());
        return repository.save(ies);
    }

    public InstituicaoEnsinoSuperior atualizar(Long id, InstituicaoRequest request) {
        InstituicaoEnsinoSuperior ies = buscar(id);
        ies.setNome(request.getNome());
        ies.setEndereco(request.getEndereco());
        ies.setTelefone(request.getTelefone());
        return repository.save(ies);
    }
}
