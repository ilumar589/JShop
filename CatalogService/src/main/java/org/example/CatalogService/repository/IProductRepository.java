package org.example.CatalogService.repository;

import org.example.CatalogService.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface IProductRepository extends ReactiveMongoRepository<Product, String> {
    Flux<Product> findProductByNameContainsIgnoreCase(String name, Pageable pageable);
}
