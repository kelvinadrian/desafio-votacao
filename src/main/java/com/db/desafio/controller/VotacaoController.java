package com.db.desafio.controller;

import com.db.desafio.builder.AssociadoBuild;
import com.db.desafio.builder.PautaBuild;
import com.db.desafio.builder.SessaoBuild;
import com.db.desafio.builder.VotoBuild;
import com.db.desafio.dto.*;
import com.db.desafio.interfaces.AssociadoInterface;
import com.db.desafio.interfaces.PautaInterface;
import com.db.desafio.interfaces.SessaoInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/votacao")
public class VotacaoController {

    private final SessaoInterface sessaoService;
    private final PautaInterface pautaService;
    private final AssociadoInterface associadoService;

    public VotacaoController(SessaoInterface sessaoService, PautaInterface pautaService, AssociadoInterface associadoService) {
        this.sessaoService = sessaoService;
        this.pautaService = pautaService;
        this.associadoService = associadoService;
    }

    @PostMapping("/sessao")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sessao criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
    })
    @Operation(summary = "Cria uma nova sessão")
    public ResponseEntity<SessaoResponse> criarSessao(@Valid @RequestBody SessaoRequest sessaoRequest) {
        SessaoResponse sessaoResponse = new SessaoResponse(sessaoService.criarSessao(SessaoBuild.build(sessaoRequest)));
        return ResponseEntity.ok(sessaoResponse);
    }

    @PostMapping("/sessao/{id}/votar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Voto computado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Sessão encerrada ou associado já votou"),
            @ApiResponse(responseCode = "404", description = "Sessão não encontrada"),
    })
    @Operation(summary = "Vota em uma sessão de uma pauta")
    public ResponseEntity<VotoResponse> votar(@PathVariable Long id, @Valid @RequestBody VotoRequest votoRequest) {
        VotoResponse votoResponse = new VotoResponse(sessaoService.votar(id, VotoBuild.build(votoRequest)));
        return ResponseEntity.ok(votoResponse);
    }

    @PostMapping("/pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pauta criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
    })
    @Operation(summary = "Cria uma nova pauta")
    public ResponseEntity<PautaResponse> criarPauta(HttpServletRequest request, @Valid @RequestBody PautaRequest pautaRequest) {
        PautaResponse pautaResponse = new PautaResponse(pautaService.criarPauta(PautaBuild.build(pautaRequest)));
        return ResponseEntity.ok(pautaResponse);
    }

    @GetMapping("/pautas/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pauta encontrada"),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada"),
    })
    @Operation(summary = "Busca uma pauta por ID")
    public ResponseEntity<PautaResponse> buscarPautaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(new PautaResponse(pautaService.buscarPautaPorId(id)));
    }

    @GetMapping("/pauta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pautas encontradas"),
            @ApiResponse(responseCode = "204", description = "Nenhuma pauta cadastrada"),
    })
    @Operation(summary = "Lista todas as pautas")
    public ResponseEntity<List<PautaResponse>> listarPautas() {
        List<PautaResponse> pautasResponse = pautaService.listarPautas()
                .stream()
                .map(PautaResponse::new)
                .toList();

        return ResponseEntity.ok(pautasResponse);
    }

    @GetMapping("/pauta/{id}/resultado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado da pauta"),
            @ApiResponse(responseCode = "403", description = "Pauta ainda não foi votada"),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada"),
    })
    @Operation(summary = "Busca o resultado de uma pauta por ID")
    public ResponseEntity<ResultadoResponse> buscarPautaResultadoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pautaService.buscarResultadoPorPautaId(id));
    }

    @PostMapping("/associado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associado criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
    })
    @Operation(summary = "Cria um novo associado")
    public ResponseEntity<AssociadoResponse> criarAssociado(HttpServletRequest request, @Valid @RequestBody AssociadoRequest associadoRequest) {
        AssociadoResponse associadoResponse = new AssociadoResponse(associadoService.criarAssociado(AssociadoBuild.build(associadoRequest)));
        return ResponseEntity.ok(associadoResponse);
    }
}
