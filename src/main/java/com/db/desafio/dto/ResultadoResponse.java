package com.db.desafio.dto;

public record ResultadoResponse(
        Long id,
        int totalVotos,
        int totalVotosSim,
        int totalVotosNao,
        String resultado
) {
}
