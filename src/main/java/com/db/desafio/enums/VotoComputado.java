package com.db.desafio.enums;

import java.util.Arrays;

public enum VotoComputado {
    SIM,
    NAO;

    public static VotoComputado fromText(String text) {
        return Arrays.stream(values())
                .filter(valor -> valor.name().equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new RuntimeException ("Valor de voto inválido: " + text));
    }
}
