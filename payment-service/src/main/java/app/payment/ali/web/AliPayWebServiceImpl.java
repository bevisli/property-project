package app.payment.ali.web;

import app.payment.ali.service.pay.PagePayService;
import app.payment.ali.service.pay.PayTransactionService;
import app.payment.api.AliPayWebService;
import app.payment.api.ali.pay.PagePayExecuteResponse;
import app.payment.api.ali.pay.PagePayRedirectResponse;
import app.payment.api.ali.pay.PagePayRequest;
import app.payment.api.ali.query.PayTransactionResponse;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author mort
 */
public class AliPayWebServiceImpl implements AliPayWebService {
    @Inject
    PagePayService pagePayService;
    @Inject
    PayTransactionService payTransactionService;

    @Override
    public PagePayExecuteResponse executePayment(PagePayRequest request) {
        return pagePayService.executePayment(request);
    }

    @Override
    public PagePayRedirectResponse redirectPayment(PagePayRequest request) {
        return pagePayService.redirectPayment(request);
    }

    @Override
    public PayTransactionResponse query(String transactionId) {
        ActionLogContext.put("id", transactionId);
        return payTransactionService.payTransaction(transactionId);
    }
}
