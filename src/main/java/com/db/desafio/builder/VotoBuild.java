package com.db.desafio.builder;

import com.db.desafio.dto.VotoRequest;
import com.db.desafio.entity.Associado;
import com.db.desafio.entity.Voto;
import com.db.desafio.enums.VotoComputado;

public interface VotoBuild {

    static Voto build(VotoRequest votoRequest) {
        return Voto.builder()
                .associado(Associado.builder().id(votoRequest.idAssociado()).build())
                .votoComputado(VotoComputado.fromText(votoRequest.voto()))
                .build();
    }
}
