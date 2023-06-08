package com.db.desafio.dto;

import com.db.desafio.entity.Pauta;

import java.time.format.DateTimeFormatter;

public record PautaResponse(
        Long id,
        String nome,
        String descricao,
        String dataCriacao
) {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public PautaResponse(Pauta pauta) {
        this(pauta.getId(), pauta.getTitulo(), pauta.getDescricao(), pauta.getDataCriacao().format(formatter));
    }
}
