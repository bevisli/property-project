package app.payment.wx.service.query;

import app.payment.wx.exception.ErrorCodes;
import app.payment.wx.exception.WXPayException;
import app.payment.wx.service.api.APIConstants;
import app.payment.wx.service.api.PaymentQueryResponse;
import com.github.wxpay.sdk.WXPay;
import core.framework.inject.Inject;
import core.framework.util.Maps;

import java.util.Map;

/**
 * @author mort
 */
public class PaymentQueryService {
    @Inject
    WXPay wxPay;

    public PaymentQueryResponse query(String transactionId) {
        Map<String, String> params = Maps.newHashMap();
        params.put(APIConstants.PARAM_OUT_TRADE_NO, transactionId);
        try {
            Map<String, String> responseMaps = wxPay.orderQuery(params);
            return PaymentQueryResponse.fromMap(responseMaps);
        } catch (Exception e) {
            throw new WXPayException("query order failed, paymentId=" + transactionId, ErrorCodes.WX_QUERY_ORDER_FAILED, e);
        }
    }
}
