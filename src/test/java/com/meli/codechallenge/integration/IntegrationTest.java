package com.meli.codechallenge.integration;


import com.meli.codechallenge.dto.RequestData;
import com.meli.codechallenge.handler.HealthHandler;
import com.meli.codechallenge.handler.MutantHandler;
import com.meli.codechallenge.router.Router;
import com.meli.codechallenge.service.MutantService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import redis.embedded.RedisServer;

import java.util.Arrays;

@WebFluxTest({Router.class, HealthHandler.class, MutantHandler.class, MutantService.class})
class IntegrationTest {

    @Autowired
    private WebTestClient client;

    private static RedisServer redisServer;

    @BeforeAll
    static void setUp(){
        redisServer = new RedisServer();
        redisServer.start();
    }

    @AfterAll
    static void destroy(){
        redisServer.stop();
    }

    @Test
    void test_healthCheck(){
        client.get()
                .uri("/_ah/health")
                .exchange()
                .expectStatus().isOk();
    }


    @Test
    void test_validationOk_ATSequence_isMutant(){

        client.post()
                .uri("/mutant")
                .bodyValue(new RequestData(Arrays.asList("AAAAT", "AATCT", "TCAGT", "TCCAT", "TCCAT")))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void test_validationOk_CGSequence_isMutant(){

        client.post()
                .uri("/mutant")
                .bodyValue(new RequestData(Arrays.asList("CCCCG", "AATCG", "TCAGG", "TCCAG", "TCCAT")))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void test_validationOkHuman_isMutant(){

        client.post()
                .uri("/mutant")
                .bodyValue(new RequestData(Arrays.asList("ATCG", "AATC", "TCAG", "TCCA")))
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void test_nullDnaSequence_isMutant(){
        client.post()
                .uri("/mutant")
                .bodyValue(new RequestData(null))
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void test_invalidDnaSequence_invalidMatrix_isMutant(){
        client.post()
                .uri("/mutant")
                .bodyValue(new RequestData(Arrays.asList("AAAAA", "AATC", "TCG", "TCCA")))
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void test_invalidDnaSequence_MxNmatrix_isMutant(){
        client.post()
                .uri("/mutant")
                .bodyValue(new RequestData(Arrays.asList("AAAA", "AATC", "TCGT")))
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void test_invalidDnaSequence_nullSequence_isMutant(){
        client.post()
                .uri("/mutant")
                .bodyValue(new RequestData(Arrays.asList("AAAA", null, "TCGT", "TCGT")))
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    void test_invalidDnaSequence_invalidLetter_isMutant(){
        client.post()
                .uri("/mutant")
                .bodyValue(new RequestData(Arrays.asList("AAAB", "ABD", "TCT", "AAAB")))
                .exchange()
                .expectStatus().isForbidden();
    }
}
