package app.payment.wx.service.api;

/**
 * @author mort
 */
public class BaseResponse {
    public String returnCode;
    public String returnMessage;
    public String resultCode;
    public String errorCode;
    public String errorCodeDesc;

    public boolean returnOK() {
        return APIConstants.RESPONSE_SUCCESS.equals(returnCode);
    }

    public boolean resultOK() {
        return APIConstants.RESPONSE_SUCCESS.equals(resultCode);
    }
}
