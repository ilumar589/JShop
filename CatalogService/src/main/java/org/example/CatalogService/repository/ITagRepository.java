package org.example.CatalogService.repository;

import org.example.CatalogService.entity.Tag;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITagRepository extends ReactiveMongoRepository<Tag, String> {
}
