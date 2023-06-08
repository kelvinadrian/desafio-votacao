package com.db.desafio.interfaces;

import com.db.desafio.dto.ResultadoResponse;
import com.db.desafio.entity.Pauta;

import java.util.List;

public interface PautaInterface {

    Pauta criarPauta(Pauta pauta);

    Pauta buscarPautaPorId(Long id);

    List<Pauta> listarPautas();

    ResultadoResponse buscarResultadoPorPautaId(Long id);
}
