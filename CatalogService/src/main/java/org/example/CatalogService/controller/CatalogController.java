package org.example.CatalogService.controller;

import error.ApiError;
import error.ApiErrorException;
import error.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.example.CatalogService.dto.ProductCreationRequest;
import org.example.CatalogService.entity.Product;
import org.example.CatalogService.service.IProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
public record CatalogController(IProductService productService) {

    private static final String root = "/api/v1/catalog/";

    @Bean
    RouterFunction<ServerResponse> composeCatalogRoutes() {
        return findAll()
                .and(findById())
                .and(createProduct())
                .and(updateProduct());
    }


    private RouterFunction<ServerResponse> findAll() {
        return route(GET(root),
                request -> ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(productService
                                .findAll()
                                .doOnError(error -> log.error(error.getMessage()))
                                .switchIfEmpty(Mono
                                        .error(new ApiErrorException(new ApiError(HttpStatus.NOT_FOUND.name(), ErrorMessage.DATA_NOT_FOUND)))),
                                Product.class)); // need error handler for custom exceptions
    }

    private RouterFunction<ServerResponse> findById() {
        return route(GET(root.concat("{id}")),
                request -> {
                    final String id = request.pathVariable("id");

                    if (!StringUtils.hasLength(id)) {
                        log.error("Invalid input parameter for id {}", id);
                        return badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new ApiError(HttpStatus.BAD_REQUEST.name(), ErrorMessage.INVALID_COUNTRY_PARAM));
                    }

                    return ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(productService
                                    .findById(id)
                                    .doOnError(error -> log.error(error.getMessage()))
                                            .switchIfEmpty(Mono
                                                    .error(new ApiErrorException(new ApiError(HttpStatus.NOT_FOUND.name(), ErrorMessage.DATA_NOT_FOUND)))),
                                    Product.class);
                });
    }

    private RouterFunction<ServerResponse> createProduct() {
        return route(POST(root), request -> request.bodyToMono(ProductCreationRequest.class)
                .doOnNext(productService::createProduct)
                .then(ok().build()));
    }

    private RouterFunction<ServerResponse> updateProduct() {
        return route(PUT(root), request -> request.bodyToMono(Product.class)
                .doOnNext(productService::updateProduct)
                .then(ok().build()));
    }
}
