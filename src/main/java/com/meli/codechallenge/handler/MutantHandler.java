package com.meli.codechallenge.handler;

import com.meli.codechallenge.dto.RequestData;
import com.meli.codechallenge.service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class MutantHandler {

    private final MutantService mutantService;

    @Autowired
    public MutantHandler(MutantService mutantService) {
        this.mutantService = mutantService;
    }

    public Mono<ServerResponse> isMutant(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(RequestData.class)
                .flatMap(mutantService::applyMutantValidation);
    }
}
