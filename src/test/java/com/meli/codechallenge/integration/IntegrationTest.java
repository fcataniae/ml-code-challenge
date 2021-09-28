package com.meli.codechallenge.integration;


import com.meli.codechallenge.handler.HealthHandler;
import com.meli.codechallenge.router.HealthRouter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest({HealthRouter.class, HealthHandler.class})
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

}
