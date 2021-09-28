package com.meli.codechallenge.integration;


import com.meli.codechallenge.dto.RequestData;
import com.meli.codechallenge.handler.HealthHandler;
import com.meli.codechallenge.handler.MutantHandler;
import com.meli.codechallenge.router.Router;
import com.meli.codechallenge.service.MutantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest({Router.class, HealthHandler.class, MutantHandler.class, MutantService.class})
class IntegrationTest {

    @Autowired
    private WebTestClient client;

    @Test
    void test_healthCheck(){
        client.get()
                .uri("/_ah/health")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void test_validationOk_isMutant(){

        client.post()
                .uri("/mutant")
                .bodyValue(new RequestData(new String[]{"AAAA", "AATC", "TCAG", "TCCA"}))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void test_validationOkHuman_isMutant(){

        client.post()
                .uri("/mutant")
                .bodyValue(new RequestData(new String[]{"ATCG", "AATC", "TCAG", "TCCA"}))
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void test_nullDnaSequence_isMutant(){
        client.post()
                .uri("/mutant")
                .bodyValue(new RequestData())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void test_invalidDnaSequence_invalidMatrix_isMutant(){
        client.post()
                .uri("/mutant")
                .bodyValue(new RequestData(new String[]{"AAAAA", "AATC", "TCG", "TCCA"}))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void test_invalidDnaSequence_MxNmatrix_isMutant(){
        client.post()
                .uri("/mutant")
                .bodyValue(new RequestData(new String[]{"AAAA", "AATC", "TCGT"}))
                .exchange()
                .expectStatus().isBadRequest();
    }



}
