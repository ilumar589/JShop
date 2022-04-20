package org.example.CatalogService.service;

import org.example.CatalogService.dto.ProductCreationRequest;
import org.example.CatalogService.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductService {

    Mono<Product> findById(String id);
    Flux<Product> findAll();
    Mono<Product> createProduct(ProductCreationRequest productCreationRequest);
    Mono<Product> updateProduct(Product productUpdateRequest);
}
