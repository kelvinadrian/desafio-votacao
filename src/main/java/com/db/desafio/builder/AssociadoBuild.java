package com.db.desafio.builder;

import com.db.desafio.dto.AssociadoRequest;
import com.db.desafio.entity.Associado;

public interface AssociadoBuild {

    static Associado build(AssociadoRequest associadoRequest) {
        return Associado.builder()
                .name(associadoRequest.name())
                .cpf(associadoRequest.cpf())
                .build();
    }
}
