package org.example.CatalogService.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.example.CatalogService.dto.ProductCreationRequest;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.util.Set;

import static org.example.CatalogService.entity.Product.PRODUCTS_COLLECTION;

@Document(collection = PRODUCTS_COLLECTION)
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public final class Product {

    static final String PRODUCTS_COLLECTION = "Products";
    @Id
    private String id;
    @Indexed(unique = true)
    @Field(targetType = FieldType.STRING)
    private String name;
    @Field(targetType = FieldType.STRING)
    private String description;
    @Field(targetType = FieldType.ARRAY)
    private Set<Tag> tags;
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal price;

    public Product(ProductCreationRequest creationRequest, Set<Tag> insertedTags) {
        this.id = null;
        this.name = creationRequest.name();
        this.description = creationRequest.description();
        this.tags = insertedTags;
        this.price = creationRequest.price();
    }
}
