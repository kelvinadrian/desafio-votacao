package com.db.desafio.service;

import com.db.desafio.entity.Associado;
import com.db.desafio.entity.Pauta;
import com.db.desafio.entity.Sessao;
import com.db.desafio.entity.Voto;
import com.db.desafio.exception.ForbiddenActionException;
import com.db.desafio.exception.ResourceNotFoundException;
import com.db.desafio.interfaces.AssociadoInterface;
import com.db.desafio.interfaces.PautaInterface;
import com.db.desafio.interfaces.SessaoInterface;
import com.db.desafio.interfaces.VotoInterface;
import com.db.desafio.repository.SessaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class SessaoService implements SessaoInterface {

    private final SessaoRepository sessaoRepository;
    private final PautaInterface pautaService;
    private final AssociadoInterface associadoService;
    private final VotoInterface votoService;

    public SessaoService(final SessaoRepository sessaoRepository,
                         final PautaInterface pautaService,
                         final AssociadoInterface associadoService,
                         final VotoInterface votoService) {
        this.sessaoRepository = sessaoRepository;
        this.pautaService = pautaService;
        this.associadoService = associadoService;
        this.votoService = votoService;
    }

    @Override
    public Sessao criarSessao(Sessao sessao) {
        Pauta pauta = pautaService.buscarPautaPorId(sessao.getPauta().getId());

        LocalDateTime now = LocalDateTime.now();

        Set<Sessao> sessoesDaPauta = sessaoRepository.findAllByPautaId(pauta.getId());
        if (sessoesDaPauta.stream().anyMatch(sessaoAtual -> !isSessaoEncerrada(sessaoAtual))) {
            throw new ForbiddenActionException("Já existe uma sessão de votação aberta para esta pauta");
        }

        if (sessao.getDataInicio() != null && sessao.getDataFim() != null && sessao.getDataInicio().isAfter(sessao.getDataFim())) {
            throw new ForbiddenActionException("Data de início da sessão de votação não pode ser posterior a data de fim");
        }

        if (sessao.getDataFim() == null || sessao.getDataFim().isBefore(now)) {
            sessao.setDataFim(now.plusMinutes(1));
        }

        return sessaoRepository.save(sessao);
    }

    @Override
    public Voto votar(Long idSessao, Voto voto) {

        Sessao sessao = sessaoRepository.findById(idSessao).orElseThrow(() -> new ResourceNotFoundException("Sessão de votação não encontrada"));
        if (isSessaoEncerrada(sessao)) {
            throw new ForbiddenActionException("Sessão de votação encerrada");
        }

        Associado associado = associadoService.buscarAssociadoPorId(voto.getAssociado().getId());
        Set<Voto> votosDaPauta = votoService.buscarVotosPorPautaId(sessao.getPauta().getId());

        if (votosDaPauta.stream().anyMatch(v -> v.getAssociado().getId().equals(associado.getId()))) {
            throw new ForbiddenActionException("Associado já votou nesta pauta");
        }

        voto.setAssociado(associado);
        voto.setPauta(sessao.getPauta());
        return this.votoService.criarVoto(voto);
    }

    private boolean isSessaoEncerrada(Sessao sessao) {
        if (sessao.isSessaoEncerrada()) {
            return true;
        }
        if (sessao.getDataFim().isBefore(LocalDateTime.now())) {
            sessao.setSessaoEncerrada(true);
            sessaoRepository.save(sessao);
            return true;
        }
        return false;
    }
}
