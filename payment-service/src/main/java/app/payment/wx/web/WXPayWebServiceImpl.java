package app.payment.wx.web;

import app.payment.api.WXPayWebService;
import app.payment.api.wx.pay.NativePayRequest;
import app.payment.api.wx.pay.NativePayResponse;
import app.payment.api.wx.query.PayTransactionResponse;
import app.payment.wx.service.pay.NativePayService;
import app.payment.wx.service.pay.PayTransactionService;
import core.framework.inject.Inject;
import core.framework.log.ActionLogContext;

/**
 * @author mort
 */
public class WXPayWebServiceImpl implements WXPayWebService {
    @Inject
    NativePayService nativePayService;
    @Inject
    PayTransactionService transactionService;

    @Override
    public NativePayResponse nativePay(NativePayRequest request) {
        return nativePayService.pay(request);
    }

    @Override
    public PayTransactionResponse query(String transactionId) {
        ActionLogContext.put("id", transactionId);
        return transactionService.payTransaction(transactionId);
    }
}
