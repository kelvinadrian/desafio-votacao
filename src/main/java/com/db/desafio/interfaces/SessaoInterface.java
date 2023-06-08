package com.db.desafio.interfaces;

import com.db.desafio.entity.Sessao;
import com.db.desafio.entity.Voto;

public interface SessaoInterface {

    Sessao criarSessao(Sessao sessao);

    Voto votar(Long idSessao, Voto voto);
}
