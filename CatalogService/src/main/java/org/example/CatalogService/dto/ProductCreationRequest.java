package org.example.CatalogService.dto;

import java.math.BigDecimal;
import java.util.Set;

public record ProductCreationRequest(String name,
                                     String description,
                                     Set<TagCreationRequest> tags,
                                     BigDecimal price) {
}
