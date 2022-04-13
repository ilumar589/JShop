package org.example.CatalogService.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.util.List;

@Document
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Product {
    @Id
    private String id;
    @Field(targetType = FieldType.STRING)
    private String name;
    @Field(targetType = FieldType.STRING)
    private String description;
    @Field(targetType = FieldType.ARRAY)
    private List<String> tags;
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal price;
}
