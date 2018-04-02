package middleware.payment.wx.service.query;

import com.github.wxpay.sdk.WXPay;
import core.framework.inject.Inject;
import core.framework.util.Maps;
import middleware.payment.wx.exception.ErrorCodes;
import middleware.payment.wx.exception.WXPayException;
import middleware.payment.wx.service.api.APIConstants;
import middleware.payment.wx.service.api.WXQueryPaymentResponse;

import java.util.Map;

/**
 * @author mort
 */
public class WXQueryService {
    @Inject
    WXPay wxPay;

    public WXQueryPaymentResponse queryWithTransactionId(String transactionId) {
        Map<String, String> params = Maps.newHashMap();
        params.put(APIConstants.PARAM_TRANSACTION_ID, transactionId);
        try {
            Map<String, String> responseMaps = wxPay.orderQuery(params);
            return WXQueryPaymentResponse.fromMap(responseMaps);
        } catch (Exception e) {
            throw new WXPayException("query order failed, transactionId=" + transactionId, ErrorCodes.WX_QUERY_ORDER_FAILED, e);
        }
    }

    public WXQueryPaymentResponse queryWithPaymentId(String outTradeNo) {
        Map<String, String> params = Maps.newHashMap();
        params.put(APIConstants.PARAM_OUT_TRADE_NO, outTradeNo);
        try {
            Map<String, String> responseMaps = wxPay.orderQuery(params);
            return WXQueryPaymentResponse.fromMap(responseMaps);
        } catch (Exception e) {
            throw new WXPayException("query order failed, paymentId=" + outTradeNo, ErrorCodes.WX_QUERY_ORDER_FAILED, e);
        }
    }
}
