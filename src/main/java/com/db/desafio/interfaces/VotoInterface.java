package com.db.desafio.interfaces;

import com.db.desafio.entity.Voto;

import java.util.Set;

public interface VotoInterface {

    Voto criarVoto(Voto voto);

    Set<Voto> buscarVotosPorPautaId(Long idPauta);
}
