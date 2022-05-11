package org.example.CatalogService.dto;

import org.eclipse.collections.api.set.MutableSet;

import java.math.BigDecimal;

public record ProductCreationRequest(String name,
                                     String description,
                                     MutableSet<TagCreationRequest> tags,
                                     BigDecimal price) {
}
