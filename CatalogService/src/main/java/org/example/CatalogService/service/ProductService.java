package org.example.CatalogService.service;

import lombok.RequiredArgsConstructor;
import org.eclipse.collections.impl.factory.Sets;
import org.example.CatalogService.dto.ProductCreationRequest;
import org.example.CatalogService.entity.Product;
import org.example.CatalogService.entity.Tag;
import org.example.CatalogService.repository.IProductRepository;
import org.example.CatalogService.repository.ITagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

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
                .collect(Tag::new)
                .asUnmodifiable();

        return tagRepository
                .insert(tagsToBeCreated)
                .log()
                .collectList()
                .flatMap(tags -> productRepository.insert(new Product(productCreationRequest, Sets.mutable.ofAll(tags))))
                .log();
    }

    @Transactional
    @Override
    public Mono<Product> updateProduct(Product productUpdateRequest) {
        return productRepository.save(productUpdateRequest).log();
    }
}
