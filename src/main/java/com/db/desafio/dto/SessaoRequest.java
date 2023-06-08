package com.db.desafio.dto;

import com.db.desafio.entity.Sessao;
import jakarta.validation.constraints.NotNull;

import java.time.format.DateTimeFormatter;

public record SessaoRequest(
        @NotNull(message = "O id da pauta não pode ser nulo")
        Long idPauta,
        String dataInicio,
        String dataFim
) {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public SessaoRequest(Sessao sessao) {
        this(sessao.getPauta().getId(), sessao.getDataInicio().format(formatter), sessao.getDataFim().format(formatter));
    }
}
