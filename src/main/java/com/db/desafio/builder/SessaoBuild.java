package com.db.desafio.builder;

import com.db.desafio.dto.SessaoRequest;
import com.db.desafio.entity.Pauta;
import com.db.desafio.entity.Sessao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface SessaoBuild {

    static Sessao build(SessaoRequest sessaoRequest) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return Sessao.builder()
                .pauta(Pauta.builder().id(sessaoRequest.idPauta()).build())
                .dataInicio(LocalDateTime.parse(sessaoRequest.dataInicio(), formatter))
                .dataFim(sessaoRequest.dataFim() != null ? LocalDateTime.parse(sessaoRequest.dataFim(), formatter) : null)
                .build();
    }
}
