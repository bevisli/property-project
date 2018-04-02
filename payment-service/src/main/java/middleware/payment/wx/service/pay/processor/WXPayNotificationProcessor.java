package middleware.payment.wx.service.pay.processor;

import middleware.payment.wx.service.api.WXPayNotificationResponse;

/**
 * @author mort
 */
public interface WXPayNotificationProcessor {
    void process(WXPayNotificationResponse notification);
}
