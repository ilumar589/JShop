package error;

import org.apache.commons.lang3.StringUtils;

public record ApErrorDetail(ErrorMessage msg, String fieldName) {

    public ApErrorDetail(ErrorMessage msg) {
        this(msg, StringUtils.EMPTY);
    }
}
