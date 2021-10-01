package com.meli.codechallenge.router;

import com.meli.codechallenge.handler.HealthHandler;
import com.meli.codechallenge.handler.MutantHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration(proxyBeanMethods = false)
public class Router {

    @Bean
    public RouterFunction<ServerResponse> route(HealthHandler helloHandler,
                                                MutantHandler mutantHandler) {

        return RouterFunctions
                .route(GET("/_ah/health"), helloHandler::health)
                .andRoute(POST("/mutant").and(accept(MediaType.APPLICATION_JSON)), mutantHandler::isMutant);
    }

}
