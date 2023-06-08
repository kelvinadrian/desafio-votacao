package com.db.desafio.dto;

import com.db.desafio.entity.Associado;

public record AssociadoResponse(
        Long id,
        String name,
        String cpf
) {
    public AssociadoResponse(Associado associado) {
        this(associado.getId(), associado.getName(), associado.getCpf());
    }
}
