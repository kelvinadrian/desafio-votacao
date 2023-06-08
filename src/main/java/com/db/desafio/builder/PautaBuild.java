package com.db.desafio.builder;

import com.db.desafio.dto.PautaRequest;
import com.db.desafio.entity.Pauta;

public interface PautaBuild {

    static Pauta build(PautaRequest pautaRequest) {
        return Pauta.builder()
                .titulo(pautaRequest.titulo())
                .descricao(pautaRequest.descricao())
                .build();
    }
}
