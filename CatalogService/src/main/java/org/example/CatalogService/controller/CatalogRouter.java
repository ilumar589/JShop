package org.example.CatalogService.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CatalogRouter {
    public static final String root = "/api/v1/catalog/";

    @Bean
    RouterFunction<ServerResponse> composeCatalogRoutes(CatalogHandler catalogHandler) {
        return route(GET(root), catalogHandler::findAll)
                .andRoute(GET(root.concat("{id}")), catalogHandler::findById)
                .andRoute(POST(root), catalogHandler::createProduct)
                .andRoute(PUT(root), catalogHandler::updateProduct);
    }
}
