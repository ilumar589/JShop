package org.example.CatalogService.service;

import lombok.RequiredArgsConstructor;
import org.example.CatalogService.dto.ProductCreationRequest;
import org.example.CatalogService.entity.Product;
import org.example.CatalogService.entity.Tag;
import org.example.CatalogService.repository.IProductRepository;
import org.example.CatalogService.repository.ITagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final IProductRepository productRepository;
    private final ITagRepository tagRepository;


    @Transactional(readOnly = true)
    @Override
    public Mono<Product> findById(String id) {
        return productRepository
                .findById(id)
                .log();
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<Product> findAll() {
        return productRepository
                .findAll()
                .log();
    }

    @Transactional
    @Override
    public Mono<Product> createProduct(ProductCreationRequest productCreationRequest) {

        Set<Tag> tagsToBeCreated = productCreationRequest
                .tags()
                .stream()
                .map(Tag::new)
                .collect(Collectors.toSet());

        return tagRepository
                .insert(tagsToBeCreated)
                .log()
                .collectList()
                .flatMap(tags -> productRepository.insert(new Product(productCreationRequest, new HashSet<>(tags))))
                .log();
    }

    @Transactional
    @Override
    public Mono<Product> updateProduct(Product productUpdateRequest) {
        return productRepository.save(productUpdateRequest).log();
    }
}
