package com.db.desafio.service;

import com.db.desafio.dto.ResultadoResponse;
import com.db.desafio.entity.Pauta;
import com.db.desafio.entity.Voto;
import com.db.desafio.enums.VotoComputado;
import com.db.desafio.exception.ForbiddenActionException;
import com.db.desafio.exception.NoContentException;
import com.db.desafio.exception.ResourceNotFoundException;
import com.db.desafio.interfaces.PautaInterface;
import com.db.desafio.interfaces.VotoInterface;
import com.db.desafio.repository.PautaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PautaService implements PautaInterface {

    private final PautaRepository pautaRepository;

    private final VotoInterface votoService;

    public PautaService(final PautaRepository pautaRepository, final VotoInterface votoService) {
        this.pautaRepository = pautaRepository;
        this.votoService = votoService;
    }

    @Override
    public Pauta criarPauta(Pauta pauta) {
        return pautaRepository.save(pauta);
    }

    @Override
    public Pauta buscarPautaPorId(Long id) {
        return pautaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pauta não encontrada"));
    }

    @Override
    public List<Pauta> listarPautas() {
        List<Pauta> pautas = pautaRepository.findAll();
        if (pautas.isEmpty()) {
            throw new NoContentException();
        }
        return pautas;
    }

    @Override
    public ResultadoResponse buscarResultadoPorPautaId(Long id) {
        Pauta pauta = buscarPautaPorId(id);
        Set<Voto> votos = votoService.buscarVotosPorPautaId(id);

        if (votos.isEmpty()) {
            throw new ForbiddenActionException("Não há votos para essa pauta");
        }

        AtomicInteger votosSim = new AtomicInteger();
        AtomicInteger votosNao = new AtomicInteger();

        votos.forEach(voto -> {
            if (VotoComputado.SIM == voto.getVotoComputado()) {
                votosSim.getAndIncrement();
            } else {
                votosNao.getAndIncrement();
            }
        });

        String resultado = "Empate";
        if (votosSim.get() > votosNao.get()) {
            resultado = "Aprovada";
        } else if (votosSim.get() < votosNao.get()) {
            resultado = "Reprovada";
        }

        return new ResultadoResponse(
                pauta.getId(),
                votos.size(),
                votosSim.get(),
                votosNao.get(),
                resultado
        );
    }
}
