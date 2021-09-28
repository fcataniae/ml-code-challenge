package com.meli.codechallenge.router;

import com.meli.codechallenge.handler.HealthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration(proxyBeanMethods = false)
public class HealthRouter {

    @Bean
    public RouterFunction<ServerResponse> route(HealthHandler helloHandler) {

        return RouterFunctions
                .route(GET("/_ah/health"), helloHandler::hello);
    }

}
