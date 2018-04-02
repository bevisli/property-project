package middleware.payment.ali.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayResponse;
import middleware.payment.api.ali.ErrorView;

/**
 * @author mort
 */
public class AliErrorService {
    public ErrorView resultError(AlipayResponse alipayResponse, String message) {
        ErrorView view = new ErrorView();
        view.errorCode = alipayResponse.getCode();
        view.errorMessage = message;
        return view;
    }

    public ErrorView apiException(AlipayApiException e, String errorCode) {
        ErrorView error = new ErrorView();
        error.errorCode = errorCode;
        error.errorMessage = e.getMessage();
        return error;
    }
}
