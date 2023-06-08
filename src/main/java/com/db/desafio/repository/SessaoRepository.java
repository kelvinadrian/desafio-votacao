package com.db.desafio.repository;

import com.db.desafio.entity.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    Set<Sessao> findAllByPautaId(Long idPauta);
}
