package org.example.CatalogService.controller;

import dto.ApplicationResponse;
import error.ApiError;
import error.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.CatalogService.dto.ProductCreationRequest;
import org.example.CatalogService.entity.Product;
import org.example.CatalogService.service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(path ="/api/v1/catalog/")
public final class CatalogController {
    private final IProductService productService;

    @GetMapping(path = "{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<ResponseEntity<ApplicationResponse<Product>>> findById(@PathVariable("id") String id) {

        if (!StringUtils.hasLength(id)) {
            log.error("Invalid input parameter for id {}", id);
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(new ApplicationResponse<>(Optional.empty(), Optional.of(new ApiError(HttpStatus.BAD_REQUEST.name(), ErrorMessage.INVALID_COUNTRY_PARAM)))));
        }

        return productService
                .findById(id)
                .map(product -> ResponseEntity
                        .ok(new ApplicationResponse<>(Optional.of(product), Optional.empty())))
                .onErrorReturn(ResponseEntity
                        .internalServerError()
                        .body(new ApplicationResponse<>(Optional.empty(), Optional.of(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.name(), ErrorMessage.INTERNAL_ERROR)))));
    }


    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<ResponseEntity<ApplicationResponse<List<Product>>>> findAll() {
        return productService
                .findAll()
                .collectList()
                .map(products -> ResponseEntity
                        .ok(new ApplicationResponse<>(Optional.of(products), Optional.empty())))
                .onErrorReturn(ResponseEntity
                        .internalServerError()
                        .body(new ApplicationResponse<>(Optional.empty(), Optional.of(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.name(), ErrorMessage.INTERNAL_ERROR)))));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<ResponseEntity<ApplicationResponse<Product>>> createProduct(@RequestBody ProductCreationRequest productCreationRequest) {
        return productService
                .createProduct(productCreationRequest)
                .map(product -> ResponseEntity.ok(new ApplicationResponse<>(Optional.of(product), Optional.empty())))
                .onErrorReturn(ResponseEntity
                        .internalServerError()
                        .body(new ApplicationResponse<>(Optional.empty(), Optional.of(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.name(), ErrorMessage.INTERNAL_ERROR)))));
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<ResponseEntity<ApplicationResponse<Product>>> updateProduct(@RequestBody Product productUpdateRequest) {
        return productService.updateProduct(productUpdateRequest)
                .map(product -> ResponseEntity.ok(new ApplicationResponse<>(Optional.of(product), Optional.empty())))
                .onErrorReturn(ResponseEntity
                        .internalServerError()
                        .body(new ApplicationResponse<>(Optional.empty(), Optional.of(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.name(), ErrorMessage.INTERNAL_ERROR)))));
    }
}
