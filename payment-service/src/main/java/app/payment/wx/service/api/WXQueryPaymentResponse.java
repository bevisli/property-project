package app.payment.wx.service.api;

import app.payment.util.Converters;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author mort
 */
public class WXQueryPaymentResponse extends BaseResponse {
    public String appId;
    public String merchantId;

    public String transactionId;
    public String outTradeNo;
    public String openId;
    public String tradeType;
    public String tradeState;
    public String tradeStateDesc;
    public String bankType;
    public Double totalFee;
    public Double cashFee;
    public String feeType;
    public LocalDateTime timeEnd;

    public static WXQueryPaymentResponse fromMap(Map<String, String> params) {
        WXQueryPaymentResponse response = new WXQueryPaymentResponse();
        response.returnCode = params.get(APIConstants.PARAM_RETURN_CODE);
        response.returnMessage = params.get(APIConstants.PARAM_RETURN_MSG);
        if (response.returnOK()) {
            response.appId = params.get(APIConstants.PARAM_APP_ID);
            response.merchantId = params.get(APIConstants.PARAM_MCH_ID);
            response.resultCode = params.get(APIConstants.PARAM_RESULT_CODE);
            response.errorCode = params.get(APIConstants.PARAM_ERR_CODE);
            response.errorCodeDesc = params.get(APIConstants.PARAM_ERR_CODE_DES);
        }
        if (response.resultOK()) {
            response.tradeState = params.get(APIConstants.PARAM_TRADE_STATE);
            response.tradeStateDesc = params.get(APIConstants.PARAM_TRADE_STATE_DESC);
            response.outTradeNo = params.get(APIConstants.PARAM_OUT_TRADE_NO);
        }
        if (response.tradeOK()) {
            response.openId = params.get(APIConstants.PARAM_OPEN_ID);
            response.tradeType = params.get(APIConstants.PARAM_TRADE_TYPE);
            response.transactionId = params.get(APIConstants.PARAM_TRANSACTION_ID);
            response.bankType = params.get(APIConstants.PARAM_BANK_TYPE);
            response.totalFee = formatDouble(params.get(APIConstants.PARAM_TOTAL_FEE));
            response.cashFee = formatDouble(params.get(APIConstants.PARAM_CASH_FEE));
            response.feeType = params.getOrDefault(APIConstants.PARAM_FEE_TYPE, null);
            response.timeEnd = Converters.toLocalDateTime(params.get(APIConstants.PARAM_TIME_END), Converters.WEI_TIME_FORMAT);
        }
        return response;
    }

    private static Double formatDouble(String value) {
        if (value == null) return null;
        return Converters.divide(Integer.valueOf(value), 100, 2);
    }

    private boolean tradeOK() {
        return APIConstants.RESPONSE_SUCCESS.equals(tradeState);
    }
}
