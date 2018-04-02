package middleware.alicloud.oss;

import core.framework.log.ErrorCode;
import core.framework.log.Severity;

/**
 * @author mort
 */
public class AliOSSException extends RuntimeException implements ErrorCode {
    public static final String DEFAULT_ERROR_CODE = "ALI_OSS_SERVICE_FAILED";
    private static final long serialVersionUID = -5679750824799236327L;

    private final String errorCode;

    public AliOSSException(String message) {
        super(message);
        errorCode = DEFAULT_ERROR_CODE;
    }

    public AliOSSException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public AliOSSException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    @Override
    public String errorCode() {
        return errorCode;
    }

    @Override
    public Severity severity() {
        return Severity.WARN;
    }
}
