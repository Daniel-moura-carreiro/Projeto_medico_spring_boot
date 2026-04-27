package com.github.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.github.app.model.paciente.DadosAtualizacaoPaciente;
import com.github.app.model.paciente.DadosCadastroPaciente;
import com.github.app.model.paciente.DadosListagemPaciente;
import com.github.app.model.paciente.Paciente;
import com.github.app.model.paciente.PacienteRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;
    
    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroPaciente dados) {
        repository.save(new Paciente(dados));
    }

    @GetMapping("todos")
    public List<Paciente> listarTodos() {
        return repository.findAll();
    }

    @GetMapping("listar")
    public List<DadosListagemPaciente> listar() {
        return repository.findAll().stream().map(DadosListagemPaciente::new).toList();
    }

    @GetMapping
    public Page<DadosListagemPaciente> listarPorPagina(Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemPaciente::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody DadosAtualizacaoPaciente dados) {
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    // @DeleteMapping("/{id}")
    // @Transactional
    // public void alterarStatus(@PathVariable Integer id) {
    //     var paciente = repository.getReferenceById(id);
    //     paciente.exclusaoLogica();
    // }
}