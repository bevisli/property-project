package middleware.payment.util;

import core.framework.log.ErrorCode;

/**
 * @author mort
 */
public class QRCodeException extends RuntimeException implements ErrorCode {
    private static final long serialVersionUID = 4373504551551358251L;

    public QRCodeException(String message) {
        super(message);
    }

    public QRCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String errorCode() {
        return "QR_CODE_GENERATE_FAILED";
    }
}
