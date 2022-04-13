package error;

public record ApiError(int code, String status, String errorMessage) {
}
