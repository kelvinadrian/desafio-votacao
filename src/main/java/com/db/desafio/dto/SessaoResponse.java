package com.db.desafio.dto;

import com.db.desafio.entity.Sessao;

import java.time.format.DateTimeFormatter;

public record SessaoResponse(
        Long id,
        PautaResponse pauta,
        String dataCriacao,
        String dataFechamento,
        boolean sessaoEncerrada
) {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public SessaoResponse(Sessao sessao) {
        this(sessao.getId(), new PautaResponse(sessao.getPauta()), sessao.getDataInicio().format(formatter), sessao.getDataFim().format(formatter), sessao.isSessaoEncerrada());
    }
}
