package com.db.desafio.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Associado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false, length = 14)
    @CPF
    private String cpf;
}
