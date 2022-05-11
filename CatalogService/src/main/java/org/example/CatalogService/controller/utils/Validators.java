package org.example.CatalogService.controller.utils;

import error.ApErrorDetail;
import error.ApiError;
import error.ApiErrorException;
import error.ErrorMessage;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.example.CatalogService.dto.ProductCreationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.util.Objects;

public final class Validators {

    private Validators() {}

    public static void validateProductCreationRequest(ProductCreationRequest productCreationRequest) {
        MutableList<ApErrorDetail> errorDetails = new FastList<>();

        if (!StringUtils.hasLength(productCreationRequest.name())) {
           errorDetails.add(new ApErrorDetail(ErrorMessage.MISSING_FIELD_VALUE, "ProductCreationRequest.name"));
        }

        if (Objects.isNull(productCreationRequest.price())) {
            errorDetails.add(new ApErrorDetail(ErrorMessage.MISSING_FIELD_VALUE, "ProductCreationRequest.price"));
        }

        if (!errorDetails.isEmpty()) {
            throw new ApiErrorException(new ApiError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), errorDetails.toImmutableList()));
        }
    }
}
