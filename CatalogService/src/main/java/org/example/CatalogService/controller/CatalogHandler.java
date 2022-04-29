package org.example.CatalogService.controller;

import error.ApiError;
import error.ApiErrorException;
import error.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.CatalogService.dto.ProductCreationRequest;
import org.example.CatalogService.entity.Product;
import org.example.CatalogService.service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@Slf4j
@RequiredArgsConstructor
public class CatalogHandler {
    private final IProductService productService;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService
                                .findAll()
                                .doOnError(error -> log.error(error.getMessage()))
                                .switchIfEmpty(Mono
                                        .error(new ApiErrorException(new ApiError(HttpStatus.NOT_FOUND.name(), ErrorMessage.DATA_NOT_FOUND)))),
                        Product.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
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
    }

    public Mono<ServerResponse> createProduct(ServerRequest request) {
        return request.bodyToMono(ProductCreationRequest.class)
                .doOnNext(productService::createProduct)
                .then(ok().build());
    }

    public Mono<ServerResponse> updateProduct(ServerRequest request) {
        return request.bodyToMono(Product.class)
                .doOnNext(productService::updateProduct)
                .then(ok().build());
    }
}
