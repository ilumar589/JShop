package org.example.CatalogService.entity;

import lombok.*;
import org.example.CatalogService.dto.TagCreationRequest;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = Tag.TAG_COLLECTION)
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    static final String TAG_COLLECTION = "Tags";

    @Id
    private String id;

    @Indexed(unique = true)
    @Field(targetType = FieldType.STRING)
    private String name;

    public Tag(TagCreationRequest tagCreationRequest) {
        this.id = null;
        this.name = tagCreationRequest.name();
    }
}
