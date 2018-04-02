package app.payment.ali.service.pay;

import app.payment.AliPayModule;
import app.payment.ali.AliPayConfig;
import app.payment.ali.domain.AliPayTransaction;
import app.payment.ali.exception.ErrorCodes;
import app.payment.ali.service.AliErrorService;
import app.payment.ali.service.api.PayContent;
import app.payment.api.ali.pay.PagePayExecuteResponse;
import app.payment.api.ali.pay.PagePayRedirectResponse;
import app.payment.api.ali.pay.PagePayRequest;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.request.AlipayTradePagePayRequest;
import core.framework.impl.log.marker.ErrorCodeMarker;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.util.Strings;
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
    PayTransactionService transactionService;

    public PagePayExecuteResponse executePayment(PagePayRequest request) {
        AliPayTransaction transaction = transactionService.addAliPayTransaction(request);
        PagePayExecuteResponse response = new PagePayExecuteResponse();
        response.transactionId = transaction.id;
        try {
            AlipayTradePagePayRequest payRequest = tradePagePayRequest(transaction.id, request);
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
        PagePayRedirectResponse response = new PagePayRedirectResponse();
        response.transactionId = transaction.id;
        try {
            AlipayTradePagePayRequest payRequest = tradePagePayRequest(transaction.id, request);
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
        content.outTradeNo = transactionId;
        content.productCode = "FAST_INSTANT_TRADE_PAY";
        content.totalAmount = request.amount.toString();
        content.subject = request.productName;
        content.body = request.description;
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
