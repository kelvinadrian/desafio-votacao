package com.db.desafio.interfaces;

import com.db.desafio.entity.Associado;

public interface AssociadoInterface {

    Associado criarAssociado(Associado associado);

    Associado buscarAssociadoPorId(Long id);
}
