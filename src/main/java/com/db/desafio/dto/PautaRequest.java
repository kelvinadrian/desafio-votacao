package com.db.desafio.dto;

import com.db.desafio.entity.Pauta;
import jakarta.validation.constraints.NotBlank;

public record PautaRequest(

        @NotBlank(message = "O título da pauta não pode ser vazio")
        String titulo,

        @NotBlank(message = "A descrição da pauta não pode ser vazia")
        String descricao
) {
        public PautaRequest(Pauta pauta){
                this(pauta.getTitulo(), pauta.getDescricao());
        }
}
