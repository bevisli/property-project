package middleware.payment.wx.service.api;

import middleware.payment.util.Converters;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author mort
 */
public class WXPayNotificationResponse extends BaseResponse {
    public String appId;
    public String merchantId;
    public String openId;
    public String tradeType;
    public String bankType;
    public String feeType;
    public Double totalFee;
    public Double cashFee;
    public Double couponFee;
    public Integer couponCount;
    public String transactionId;
    public String outTradeNo;
    public String attachParam;
    public LocalDateTime timeEnd;

    public static WXPayNotificationResponse fromMap(Map<String, String> params) {
        WXPayNotificationResponse notification = new WXPayNotificationResponse();
        notification.returnCode = params.get(APIConstants.PARAM_RETURN_CODE);
        notification.returnMessage = params.get(APIConstants.PARAM_RETURN_MSG);
        if (notification.returnOK()) {
            notification.appId = params.get(APIConstants.PARAM_APP_ID);
            notification.merchantId = params.get(APIConstants.PARAM_MCH_ID);
            notification.openId = params.get(APIConstants.PARAM_OPEN_ID);
            notification.transactionId = params.get(APIConstants.PARAM_TRANSACTION_ID);
            notification.outTradeNo = params.get(APIConstants.PARAM_OUT_TRADE_NO);
            notification.tradeType = params.get(APIConstants.PARAM_TRADE_TYPE);
            notification.bankType = params.get(APIConstants.PARAM_BANK_TYPE);
            notification.feeType = params.getOrDefault(APIConstants.PARAM_FEE_TYPE, null);
            notification.totalFee = formatDouble(params.get(APIConstants.PARAM_TOTAL_FEE));
            notification.cashFee = formatDouble(params.get(APIConstants.PARAM_CASH_FEE));
            notification.couponFee = formatDouble(params.get(APIConstants.PARAM_COUPON_FEE));
            notification.couponCount = Converters.toInteger(params.get(APIConstants.PARAM_COUPON_COUNT));
            notification.timeEnd = Converters.toLocalDateTime(params.get(APIConstants.PARAM_TIME_END), Converters.WEI_TIME_FORMAT);
            notification.resultCode = params.get(APIConstants.PARAM_RESULT_CODE);
            notification.errorCode = params.get(APIConstants.PARAM_ERR_CODE);
            notification.errorCodeDesc = params.get(APIConstants.PARAM_ERR_CODE_DES);
            notification.attachParam = params.get(APIConstants.PARAM_ATTACH);
        }

        return notification;
    }

    private static Double formatDouble(String value) {
        if (value == null) return null;
        return Converters.divide(Integer.valueOf(value), 100, 2);
    }
}
