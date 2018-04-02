package middleware.payment.ali.service.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.request.AlipayTradePagePayRequest;
import core.framework.impl.log.marker.ErrorCodeMarker;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.util.Strings;
import middleware.payment.AliPayModule;
import middleware.payment.ali.AliPayConfig;
import middleware.payment.ali.domain.AliPayTransaction;
import middleware.payment.ali.exception.ErrorCodes;
import middleware.payment.ali.service.AliErrorService;
import middleware.payment.ali.service.api.PayContent;
import middleware.payment.api.ali.pay.PagePayExecuteResponse;
import middleware.payment.api.ali.pay.PagePayRedirectResponse;
import middleware.payment.api.ali.pay.PagePayRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mort
 */
public class PagePayService {
    private final Logger logger = LoggerFactory.getLogger(PagePayService.class);
    @Inject
    AliPayConfig config;
    @Inject
    AlipayClient alipayClient;
    @Inject
    AliErrorService errorService;
    @Inject
    TransactionService transactionService;

    public PagePayExecuteResponse executePayment(PagePayRequest request) {
        AliPayTransaction transaction = transactionService.addAliPayTransaction(request);
        AlipayTradePagePayRequest payRequest = tradePagePayRequest(transaction.id, request);
        PagePayExecuteResponse response = new PagePayExecuteResponse();
        try {
            AlipayResponse alipayResponse = alipayClient.pageExecute(payRequest);
            if (alipayResponse.isSuccess()) {
                response.body = alipayResponse.getBody();
                transactionService.requestCompleted(transaction);
            } else {
                transactionService.requestFailed(transaction, alipayResponse.getCode());
                String message = Strings.format("call ali pay failed, code={}, subcode={}, message={}, submessage={}", alipayResponse.getCode(), alipayResponse.getSubCode(), alipayResponse.getMsg(), alipayResponse.getSubMsg());
                logger.error(new ErrorCodeMarker(ErrorCodes.ALI_PAGE_PAY_EXECUTE_PAYMENT_ERROR), message);
                response.error = errorService.resultError(alipayResponse, message);
            }
        } catch (AlipayApiException e) {
            transactionService.requestFailed(transaction, "API_FAILED");
            logger.error(new ErrorCodeMarker(ErrorCodes.ALI_PAGE_PAY_EXECUTE_PAYMENT_ERROR), "call ali pay failed", e);
            response.error = errorService.apiException(e, ErrorCodes.ALI_PAGE_PAY_EXECUTE_PAYMENT_ERROR);
        }
        return response;
    }

    public PagePayRedirectResponse redirectPayment(PagePayRequest request) {
        AliPayTransaction transaction = transactionService.addAliPayTransaction(request);
        AlipayTradePagePayRequest payRequest = tradePagePayRequest(transaction.id, request);
        PagePayRedirectResponse response = new PagePayRedirectResponse();
        try {
            AlipayResponse alipayResponse = alipayClient.pageExecute(payRequest, "GET");
            if (alipayResponse.isSuccess()) {
                response.redirectURL = alipayResponse.getBody();
                transactionService.requestCompleted(transaction);
            } else {
                transactionService.requestFailed(transaction, alipayResponse.getCode());
                String message = Strings.format("call ali pay failed, code={}, subcode={}, message={}, submessage={}", alipayResponse.getCode(), alipayResponse.getSubCode(), alipayResponse.getMsg(), alipayResponse.getSubMsg());
                logger.error(new ErrorCodeMarker(ErrorCodes.ALI_PAGE_PAY_EXECUTE_PAYMENT_ERROR), message);
                response.error = errorService.resultError(alipayResponse, message);
            }
        } catch (AlipayApiException e) {
            transactionService.requestFailed(transaction, "API_FAILED");
            logger.error(new ErrorCodeMarker(ErrorCodes.ALI_PAGE_PAY_REDIRECT_PAYMENT_ERROR), "call ali pay failed", e);
            response.error = errorService.apiException(e, ErrorCodes.ALI_PAGE_PAY_REDIRECT_PAYMENT_ERROR);
        }
        return response;
    }

    private AlipayTradePagePayRequest tradePagePayRequest(String transactionId, PagePayRequest request) {
        AlipayTradePagePayRequest payRequest = new AlipayTradePagePayRequest();
        payRequest.setReturnUrl(request.returnURL);
        payRequest.setNotifyUrl(notifyURL());
        PayContent content = new PayContent();
        content.outTradeNo = request.paymentId;
        content.productCode = "FAST_INSTANT_TRADE_PAY";
        content.totalAmount = request.amount.toString();
        content.subject = request.productName;
        content.body = request.description;
        content.passBackParams = transactionId;
        if (!Strings.isEmpty(config.providerId)) {
            content.extendParams = new PayContent.ExtendParams();
            content.extendParams.serviceProviderId = config.providerId;
        }
        payRequest.setBizContent(JSON.toJSON(content));
        return payRequest;
    }

    private String notifyURL() {
        return Strings.format("{}{}", config.serviceHost, AliPayModule.ALI_PAY_NOTIFY_URL_PATH);
    }
}
