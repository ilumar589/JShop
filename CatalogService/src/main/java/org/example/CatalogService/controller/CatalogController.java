package org.example.CatalogService.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.CatalogService.repository.IProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@AllArgsConstructor
@Slf4j
@RestController("api/v1/catalog")
public final class CatalogController {

    private final IProductRepository productRepository;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> findById(String id) {

    }

}
