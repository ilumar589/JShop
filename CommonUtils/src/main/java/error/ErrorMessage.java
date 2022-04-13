package error;

public enum ErrorMessage {
    INVALID_COUNTRY_PARAM("Invalid country parameter"),
    INTERNAL_ERROR("Internal error");

    private String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
