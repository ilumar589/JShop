package error;

public final class ApiErrorException extends RuntimeException {
    private final ApiError error;

    public ApiErrorException(ApiError error) {
        this.error = error;
    }

    public ApiError getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return getError().toString();
    }
}
