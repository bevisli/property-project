package app.payment.wx.service.pay.processor;

import app.payment.wx.service.api.WXPayNotificationResponse;

/**
 * @author mort
 */
public interface WXPayNotificationProcessor {
    void process(WXPayNotificationResponse notification);
}
