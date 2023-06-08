package com.db.desafio.entity;

import com.db.desafio.enums.VotoComputado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "associado_id", nullable = false)
    private Associado associado;

    @ManyToOne
    @JoinColumn(name = "pauta_id", nullable = false)
    private Pauta pauta;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VotoComputado votoComputado;

    @Builder.Default
    private LocalDateTime dataCriacao = LocalDateTime.now();
}
