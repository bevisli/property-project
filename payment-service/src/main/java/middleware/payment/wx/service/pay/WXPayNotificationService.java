package middleware.payment.wx.service.pay;

import core.framework.inject.Inject;
import middleware.payment.wx.service.WXSignatureService;
import middleware.payment.wx.service.api.WXPayNotificationResponse;
import middleware.payment.wx.service.pay.processor.WXPayNotificationProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author mort
 */
public class WXPayNotificationService {
    private final Logger logger = LoggerFactory.getLogger(WXPayNotificationService.class);
    public WXPayNotificationProcessor processor;
    @Inject
    WXSignatureService signatureService;

    public void process(Map<String, String> params) {
        StringBuilder paramBuilder = new StringBuilder();
        params.forEach((name, value) -> paramBuilder.append("{}={};").append(System.lineSeparator()));
        logger.debug("params:" + paramBuilder.toString());
        if (signatureService.verifySignature(params)) {
            WXPayNotificationResponse notification = WXPayNotificationResponse.fromMap(params);
            // todo in future, we should turn API response to payment-service request/response model
            processor.process(notification);
        } else {
            logger.warn("WX_PAYMENT_NOTIFICATION_VERIFY_SIGNATURE_FAILED", "failed to verified request signature");
        }
    }
}
