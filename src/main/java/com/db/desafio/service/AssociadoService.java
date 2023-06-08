package com.db.desafio.service;

import com.db.desafio.exception.ResourceNotFoundException;
import com.db.desafio.entity.Associado;
import com.db.desafio.interfaces.AssociadoInterface;
import com.db.desafio.repository.AssociadoRepository;
import org.springframework.stereotype.Service;

@Service
public class AssociadoService implements AssociadoInterface {

    private final AssociadoRepository associadoRepository;

    public AssociadoService(final AssociadoRepository associadoRepository) {
        this.associadoRepository = associadoRepository;
    }

    @Override
    public Associado criarAssociado(Associado associado) {
        return associadoRepository.save(associado);
    }

    @Override
    public Associado buscarAssociadoPorId(Long id) {
        return associadoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Associado não encontrado"));
    }
}
