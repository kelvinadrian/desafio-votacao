package com.db.desafio.service;

import com.db.desafio.entity.Voto;
import com.db.desafio.interfaces.VotoInterface;
import com.db.desafio.repository.VotoRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VotoService implements VotoInterface {

    private final VotoRepository votoRepository;

    public VotoService(final VotoRepository votoRepository) {
        this.votoRepository = votoRepository;
    }

    @Override
    public Voto criarVoto(Voto voto) {
        return votoRepository.save(voto);
    }

    @Override
    public Set<Voto> buscarVotosPorPautaId(Long idPauta) {
        return votoRepository.findAllByPautaId(idPauta);
    }
}
