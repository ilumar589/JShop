package error;

public enum ErrorMessage {
    INVALID_COUNTRY_PARAM("Invalid country parameter"),
    INTERNAL_ERROR("Internal error"),

    DATA_NOT_FOUND("Data not found");

    private final String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
