package error;

public record ApiError(int code, String status, ErrorMessage msg) {
    public ApiError(String status, ErrorMessage msg) {
        this(0, status, msg);
    }
}
