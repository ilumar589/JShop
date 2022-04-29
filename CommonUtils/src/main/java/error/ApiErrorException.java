package error;

public final class ApiErrorException extends Throwable {
    private final ApiError error;

    public ApiErrorException(ApiError error) {
        this.error = error;
    }

    public ApiError getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return error.toString();
    }

    @Override
    public String getLocalizedMessage() {
        return error.toString();
    }
}
