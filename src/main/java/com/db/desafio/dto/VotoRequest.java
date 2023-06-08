package com.db.desafio.dto;

import com.db.desafio.entity.Voto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VotoRequest(

        @NotNull(message = "O campo idAssociado não pode ser nulo")
        Long idAssociado,

        @NotBlank(message = "O campo voto deve ser SIM ou NAO")
        String voto
) {
    public VotoRequest(Voto voto) {
        this(voto.getAssociado().getId(), voto.getVotoComputado().name());
    }
}
