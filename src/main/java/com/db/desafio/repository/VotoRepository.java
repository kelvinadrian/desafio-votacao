package com.db.desafio.repository;

import com.db.desafio.entity.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    Set<Voto> findAllByPautaId(Long idPauta);
}
