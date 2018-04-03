package app.payment.wx.service.pay.processor;

import app.payment.api.wx.pay.WXPayNotifyMessage;

/**
 * @author mort
 */
public interface WXPayNotifyProcessor {
    void process(WXPayNotifyMessage message);
}
