package com.db.desafio;

import com.db.desafio.dto.AssociadoRequest;
import com.db.desafio.dto.PautaRequest;
import com.db.desafio.dto.SessaoRequest;
import com.db.desafio.dto.VotoRequest;
import com.db.desafio.entity.Pauta;
import com.db.desafio.entity.Sessao;
import com.db.desafio.service.PautaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class VotacaoTest {
    private static final String NOME = "Kelvin Vieira";
    private static final String URI ="/votacao";

    @Autowired
    PautaService pautaService;

    @LocalServerPort
    private int port;

    @Test
    void devePersistirAssociadoComSucesso() throws JsonProcessingException {
        AssociadoRequest associadoRequest = new AssociadoRequest(NOME, cpf());

        String request = new ObjectMapper().writeValueAsString(associadoRequest);

        given()
            .port(port)
            .when()
            .contentType(ContentType.JSON)
            .body(request)
            .post(URI+"/associado")
            .then()
            .statusCode(200);
    }

    @Test
    void deveCriarUmaPauta() throws JsonProcessingException {
        Pauta pauta = Pauta.builder().build();
        pauta.setDescricao("teste");
        pauta.setTitulo("votacao");

        PautaRequest pautaRequest = new PautaRequest(pauta);

        String request = new ObjectMapper().writeValueAsString(pautaRequest);

        given()
            .port(port)
            .when()
            .contentType(ContentType.JSON)
            .body(request)
            .post(URI+"/pauta")
            .then()
            .statusCode(200);
    }

    @Test
    void deveConsultarAListaDePauta() throws JsonProcessingException {
        given()
                .port(port)
                .when()
                .get(URI+"/pauta")
                .then()
                .statusCode(200);
    }

    @Test
    void deveCriarUmaSessao() throws JsonProcessingException {
        Pauta pauta = Pauta.builder().build();
        pauta.setDescricao("teste");
        pauta.setTitulo("votacao");

        Sessao sessao = Sessao.builder().build();
        sessao.setPauta(pautaService.criarPauta(pauta));
        sessao.setDataInicio(LocalDateTime.now());
        SessaoRequest sessaoRequest = new SessaoRequest(sessao.getPauta().getId(), sessao.getDataInicio().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), null);

        String request = new ObjectMapper().writeValueAsString(sessaoRequest);

        given()
                .port(port)
                .when()
                .contentType(ContentType.JSON)
                .body(request)
                .post(URI+"/sessao")
                .then()
                .statusCode(200);
    }

    private String cpf() {
        int n = 9;
        int n1 = randomiza(n);
        int n2 = randomiza(n);
        int n3 = randomiza(n);
        int n4 = randomiza(n);
        int n5 = randomiza(n);
        int n6 = randomiza(n);
        int n7 = randomiza(n);
        int n8 = randomiza(n);
        int n9 = randomiza(n);
        int d1 = n9 * 2 + n8 * 3 + n7 * 4 + n6 * 5 + n5 * 6 + n4 * 7 + n3 * 8 + n2 * 9 + n1 * 10;

        d1 = 11 - (mod(d1, 11));

        if (d1 >= 10)
            d1 = 0;

        int d2 = d1 * 2 + n9 * 3 + n8 * 4 + n7 * 5 + n6 * 6 + n5 * 7 + n4 * 8 + n3 * 9 + n2 * 10 + n1 * 11;

        d2 = 11 - (mod(d2, 11));

        if (d2 >= 10)
            d2 = 0;

        return "" + n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + d1 + d2;
    }

    private int randomiza(int n) {
        int ranNum = (int) (Math.random() * n);
        return ranNum;
    }

    private int mod(int dividendo, int divisor) {
        return (int) Math.round(dividendo - (Math.floor(dividendo / divisor) * divisor));
    }

}
