package com.db.desafio.dto;

import com.db.desafio.entity.Voto;

import java.time.format.DateTimeFormatter;

public record VotoResponse(
        Long id,
        Long idAssociado,
        String votoComputado,
        String dataCriacao
) {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public VotoResponse(Voto voto) {
        this(voto.getId(), voto.getAssociado().getId(), voto.getVotoComputado().name(), voto.getDataCriacao().format(formatter));
    }
}
