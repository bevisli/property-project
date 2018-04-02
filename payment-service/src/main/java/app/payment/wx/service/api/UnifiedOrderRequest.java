package app.payment.wx.service.api;

import core.framework.util.Maps;

import java.util.Map;

/**
 * @author mort
 */
public class UnifiedOrderRequest {
    private static final String FEE_TYPE = APIConstants.FEE_TYP_CNY;
    private static final String TRADE_TYPE = APIConstants.TRADE_TYPE_NATIVE;
    public String body;
    public String ourTradeNo;
    public Integer totalFee;
    public String clientIP;
    public String notifyURL;
    public String attach;

    public Map<String, String> toMap() {
        Map<String, String> params = Maps.newHashMap();
        params.put(APIConstants.PARAM_BODY, body);
        params.put(APIConstants.PARAM_OUT_TRADE_NO, ourTradeNo);
        params.put(APIConstants.PARAM_FEE_TYPE, FEE_TYPE);
        params.put(APIConstants.PARAM_TOTAL_FEE, totalFee.toString());
        params.put(APIConstants.PARAM_SPBILL_CREATE_IP, clientIP);
        params.put(APIConstants.PARAM_NOTIFY_URL, notifyURL);
        params.put(APIConstants.PARAM_TRADE_TYPE, TRADE_TYPE);
        params.put(APIConstants.PARAM_ATTACH, attach);
        params.put(APIConstants.PARAM_FEE_TYPE, FEE_TYPE);
        return params;
    }
}
