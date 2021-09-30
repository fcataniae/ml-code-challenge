package com.meli.codechallenge.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class HealthHandler {

    public Mono<ServerResponse> health(ServerRequest serverRequest) {

        return ServerResponse.ok().bodyValue("up");
    }
}
