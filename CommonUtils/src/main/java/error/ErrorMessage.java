package error;

public enum ErrorMessage {
    INVALID_PRODUCT_ID("Invalid product id"),
    INTERNAL_ERROR("Internal error"),

    DATA_NOT_FOUND("Data not found");

    private final String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
