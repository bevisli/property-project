package app.payment.wx.service.api;

import java.util.Map;

/**
 * @author mort
 */
public class UnifiedOrderResponse extends BaseResponse {
    public String appId;
    public String merchantId;
    public String tradeType;
    public String prepayId;
    public String codeURL;

    public static UnifiedOrderResponse fromMap(Map<String, String> params) {
        UnifiedOrderResponse response = new UnifiedOrderResponse();
        response.returnCode = params.get(APIConstants.PARAM_RETURN_CODE);
        response.returnMessage = params.get(APIConstants.PARAM_RETURN_MSG);
        if (response.returnOK()) {
            response.resultCode = params.get(APIConstants.PARAM_RESULT_CODE);
            response.errorCode = params.get(APIConstants.PARAM_ERR_CODE);
            response.errorCodeDesc = params.get(APIConstants.PARAM_ERR_CODE_DES);
            response.appId = params.get(APIConstants.PARAM_APP_ID);
            response.merchantId = params.get(APIConstants.PARAM_MCH_ID);
        }
        if (response.resultOK()) {
            response.tradeType = params.get(APIConstants.PARAM_TRADE_TYPE);
            response.prepayId = params.get(APIConstants.PARAM_PREPAY_ID);
            response.codeURL = params.get(APIConstants.PARAM_CODE_URL);
        }
        return response;
    }
}
