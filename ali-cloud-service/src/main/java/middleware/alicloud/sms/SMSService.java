package middleware.alicloud.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import core.framework.json.JSON;
import core.framework.log.Severity;
import core.framework.web.service.RemoteServiceException;
import middleware.alicloud.error.ErrorCodes;

import java.util.UUID;

/**
 * @author mort
 */
public class SMSService {
    public static final String REGION_ID = "cn-hangzhou";
    public static final String END_POINT_NAME = "cn-hangzhou";
    public static final String SMS_DOMAIN = "sms.aliyuncs.com";
    public static final String SIGN_NAME = "南极星计划";
    public static final String SECURITY_CODE_TEMPLATE = "SMS_25775925";
    public static final String PAYMENT_NOTIFICATION_TEMPLATE = "SMS_126463162";
    public final String accessKey;
    public final String accessSecret;

    public SMSService(String accessKey, String accessSecret) {
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
    }

    public String sendPaymentNotification(String receiver, PaymentNotificationMessage message) {
        String paramString = JSON.toJSON(message);
        return send(receiver, paramString, PAYMENT_NOTIFICATION_TEMPLATE);
    }

    public String sendPaymentNotificationDY(String receiver, PaymentNotificationMessage message) {
        String paramString = JSON.toJSON(message);
        return sendDYSms(receiver, paramString, PAYMENT_NOTIFICATION_TEMPLATE);
    }

    public String sendSecurityCode(String receiver, SecurityCodeMessage message) {
        String paramString = JSON.toJSON(message);
        return send(receiver, paramString, SECURITY_CODE_TEMPLATE);
    }

    public String sendSecurityCodeDY(String receiver, SecurityCodeMessage message) {
        String paramString = JSON.toJSON(message);
        return sendDYSms(receiver, paramString, SECURITY_CODE_TEMPLATE);
    }

    private String send(String receiver, String paramString, String templateCode) {
        IAcsClient client = new DefaultAcsClient(DefaultProfile.getProfile(REGION_ID, accessKey, accessSecret));
        try {
            DefaultProfile.addEndpoint(END_POINT_NAME, REGION_ID, "Sms", SMS_DOMAIN);
            SingleSendSmsRequest request = new SingleSendSmsRequest();
            request.setSignName(SIGN_NAME);
            request.setTemplateCode(templateCode);
            request.setParamString(paramString);
            request.setRecNum(receiver);
            return client.getAcsResponse(request).getRequestId();
        } catch (ClientException e) {
            if (e.getErrCode().equals("InvalidSendSms")) {
                throw new RemoteServiceException(e.getMessage(), Severity.ERROR, ErrorCodes.ALI_SMS_SERVICE_QUOTA_EXCEEDED, e);
            }
            throw new RemoteServiceException("call ali sms service failed", Severity.ERROR, ErrorCodes.ALI_SMS_SERVICE_FAILED, e);
        }
    }

    private String sendDYSms(String receiver, String paramString, String templateCode) {
        IAcsClient client = new DefaultAcsClient(DefaultProfile.getProfile(REGION_ID, accessKey, accessSecret));
        try {
            DefaultProfile.addEndpoint(END_POINT_NAME, REGION_ID, "Dysmsapi", "dysmsapi.aliyuncs.com");
            SendSmsRequest request = new SendSmsRequest();
            request.setSignName(SIGN_NAME);
            request.setTemplateCode(templateCode);
            request.setTemplateParam(paramString);
            request.setPhoneNumbers(receiver);
            request.setOutId(UUID.randomUUID().toString());
            SendSmsResponse response = client.getAcsResponse(request);
            if (!"OK".equals(response.getCode())) {
                throw new RemoteServiceException("call ali sms service failed, code=" + response.getCode() + ", message=" + response.getMessage(), Severity.ERROR, ErrorCodes.ALI_SMS_SERVICE_FAILED);
            }
            return response.getRequestId();
        } catch (ClientException e) {
            if (e.getErrCode().equals("InvalidSendSms")) {
                throw new RemoteServiceException(e.getMessage(), Severity.ERROR, ErrorCodes.ALI_SMS_SERVICE_QUOTA_EXCEEDED, e);
            }
            throw new RemoteServiceException("call ali sms service failed", Severity.ERROR, ErrorCodes.ALI_SMS_SERVICE_FAILED, e);
        }
    }
}
