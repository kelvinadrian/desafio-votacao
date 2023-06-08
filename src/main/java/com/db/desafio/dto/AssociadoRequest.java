package com.db.desafio.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record AssociadoRequest(
        @NotBlank(message = "O nome do associado não pode ser vazio")
        String name,

        @NotBlank(message = "O CPF do associado não pode ser vazio")
        @CPF(message = "O CPF do associado deve ser válido")
        String cpf
) {
}
