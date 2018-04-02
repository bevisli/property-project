package app.payment.wx.exception;

import core.framework.log.ErrorCode;
import core.framework.log.Severity;

/**
 * @author mort
 */
public class WXPayException extends RuntimeException implements ErrorCode {
    public static final String DEFAULT_ERROR_CODE = "WX_PAY_API_ERROR";

    private static final long serialVersionUID = -7377097764855003442L;
    private final String errorCode;

    public WXPayException(String message) {
        super(message);
        errorCode = DEFAULT_ERROR_CODE;
    }

    public WXPayException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public WXPayException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    @Override
    public String errorCode() {
        return errorCode;
    }

    @Override
    public Severity severity() {
        return Severity.ERROR;
    }
}
