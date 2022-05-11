package org.example.CatalogService.controller;

import error.ApErrorDetail;
import error.ApiError;
import error.ApiErrorException;
import error.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.factory.Lists;
import org.example.CatalogService.controller.utils.Validators;
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

import java.net.URI;
import java.net.URISyntaxException;

import static org.example.CatalogService.utils.CatalogUtils.root;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@Slf4j
public record CatalogHandler(IProductService productService) {
    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService
                                .findAll()
                                .doOnError(error -> {
                                    log.error(error.getMessage());
                                    throw new ApiErrorException(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                            HttpStatus.INTERNAL_SERVER_ERROR.name(),
                                            Lists.immutable.of(new ApErrorDetail(ErrorMessage.INTERNAL_ERROR))));
                                })
                                .switchIfEmpty(Mono
                                        .error(new ApiErrorException(new ApiError(HttpStatus.NOT_FOUND.value(),
                                                HttpStatus.NOT_FOUND.name(),
                                                Lists.immutable.of(new ApErrorDetail(ErrorMessage.DATA_NOT_FOUND)))))),
                        Product.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        final String id = request.pathVariable("id");

        if (!StringUtils.hasLength(id)) {
            log.error("Invalid input parameter for id {}", id);

            return badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new ApiError(HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.name(),
                            Lists.immutable.of(new ApErrorDetail(ErrorMessage.INVALID_PRODUCT_ID))));
        }

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(findProduct(id), Product.class);
    }

    public Mono<ServerResponse> createProduct(ServerRequest request) {
        try {
            return ServerResponse.created(new URI(root))
                    .bodyValue(request.bodyToMono(ProductCreationRequest.class)
                            .doOnError(ex -> {
                                log.error(ex.getMessage());
                                throw new ApiErrorException(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                        HttpStatus.INTERNAL_SERVER_ERROR.name(),
                                        Lists.immutable.of(new ApErrorDetail(ErrorMessage.INTERNAL_ERROR))));
                            })
                            .doOnNext(Validators::validateProductCreationRequest)
                            .map(productService::createProduct));
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Mono<ServerResponse> updateProduct(ServerRequest request) {
        final String id = request.pathVariable("id");

        if (!StringUtils.hasLength(id)) {
            log.error("Invalid input parameter for id {}", id);
            return badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue((new ApiError(HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.name(),
                            Lists.immutable.of(new ApErrorDetail(ErrorMessage.INVALID_PRODUCT_ID)))));
        }

        return ok()
                .bodyValue(findProduct(id)
                        .flatMap(foundProduct -> request.bodyToMono(Product.class)
                               .doOnError(ex -> {
                                   log.error(ex.getMessage());
                                   throw new ApiErrorException(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                           HttpStatus.INTERNAL_SERVER_ERROR.name(),
                                           Lists.immutable.of(new ApErrorDetail(ErrorMessage.INTERNAL_ERROR))));
                                })
                                .map(productService::updateProduct)
                                ));

    }


    private Mono<Product> findProduct(String id) {
        return productService.findById(id)
                .doOnError(ex -> {
                    log.error(ex.getMessage());
                    throw new ApiErrorException(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            HttpStatus.INTERNAL_SERVER_ERROR.name(),
                            Lists.immutable.of(new ApErrorDetail(ErrorMessage.INTERNAL_ERROR))));
                })
                .switchIfEmpty(Mono.error(new ApiErrorException(new ApiError(HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.name(),
                        Lists.immutable.of(new ApErrorDetail(ErrorMessage.DATA_NOT_FOUND))))));
    }
}
