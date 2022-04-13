package dto;

import error.ApiError;

import java.util.Optional;

public record ApplicationResponse<T>(Optional<T> data, Optional<ApiError> error) {
}
