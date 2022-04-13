package error;

public record ApiError(int code, String status, ErrorMessage errorMessage) {
    public ApiError(String status, ErrorMessage errorMessage) {
        this(0, status, errorMessage);
    }
}
