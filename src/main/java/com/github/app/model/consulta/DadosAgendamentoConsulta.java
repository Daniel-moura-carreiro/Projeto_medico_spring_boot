package com.github.app.model.consulta;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
    Integer medicoId,
    Integer pacienteId,
    String observacao,
    String status,
    LocalDateTime  data
) {
    
}
