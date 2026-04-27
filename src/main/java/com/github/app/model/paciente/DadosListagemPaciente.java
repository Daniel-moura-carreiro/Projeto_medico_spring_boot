package com.github.app.model.paciente;

public record DadosListagemPaciente(
    Integer id,
    String nome,
    String telefone
) {
    public DadosListagemPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getTelefone());
    }
    
}