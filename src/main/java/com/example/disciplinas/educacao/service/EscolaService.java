package com.example.disciplinas.educacao.service;

import com.example.disciplinas.educacao.dto.EscolaRequest;
import com.example.disciplinas.educacao.entity.Escola;
import com.example.disciplinas.educacao.entity.InstituicaoEnsinoSuperior;
import com.example.disciplinas.educacao.exception.ResourceNotFoundException;
import com.example.disciplinas.educacao.repository.EscolaRepository;
import com.example.disciplinas.educacao.repository.InstituicaoEnsinoSuperiorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EscolaService {

    private final EscolaRepository escolaRepository;
    private final InstituicaoEnsinoSuperiorRepository iesRepository;

    public EscolaService(EscolaRepository escolaRepository, InstituicaoEnsinoSuperiorRepository iesRepository) {
        this.escolaRepository = escolaRepository;
        this.iesRepository = iesRepository;
    }

    public List<Escola> listar() {
        return escolaRepository.findAll();
    }

    public Escola buscar(Long id) {
        return escolaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Escola não encontrada com id " + id));
    }

    public Escola salvar(EscolaRequest request) {
        InstituicaoEnsinoSuperior ies = iesRepository.findById(request.getIesId())
                .orElseThrow(() -> new ResourceNotFoundException("IES não encontrada com id " + request.getIesId()));
        Escola escola = new Escola();
        escola.setNome(request.getNome());
        escola.setCoordenador(request.getCoordenador());
        escola.setAtivo(request.getAtivo() == null || request.getAtivo());
        escola.setIes(ies);
        return escolaRepository.save(escola);
    }

    public Escola atualizar(Long id, EscolaRequest request) {
        Escola escola = buscar(id);
        InstituicaoEnsinoSuperior ies = iesRepository.findById(request.getIesId())
                .orElseThrow(() -> new ResourceNotFoundException("IES não encontrada com id " + request.getIesId()));
        escola.setNome(request.getNome());
        escola.setCoordenador(request.getCoordenador());
        escola.setAtivo(request.getAtivo() == null || request.getAtivo());
        escola.setIes(ies);
        return escolaRepository.save(escola);
    }

    public Escola inativar(Long id) {
        Escola escola = buscar(id);
        escola.setAtivo(false);
        return escolaRepository.save(escola);
    }
}
