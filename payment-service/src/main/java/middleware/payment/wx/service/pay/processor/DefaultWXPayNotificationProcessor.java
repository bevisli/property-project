package middleware.payment.wx.service.pay.processor;

import core.framework.inject.Inject;
import core.framework.util.Strings;
import middleware.payment.wx.WXPayConfigImpl;
import middleware.payment.wx.exception.WXPayException;
import middleware.payment.wx.service.api.APIConstants;
import middleware.payment.wx.service.api.WXPayNotificationResponse;

/**
 * @author mort
 */
public class DefaultWXPayNotificationProcessor implements WXPayNotificationProcessor {
    @Inject
    WXPayConfigImpl config;

    @Override
    public void process(WXPayNotificationResponse notification) {
        if (Strings.isEmpty(notification.outTradeNo)) {
            throw new WXPayException("missing paymentId", "WX_PAY_NOTIFICATION_PROCESS_FAILED");
        }
        if (!config.appId.equals(notification.appId)) {
            throw new WXPayException("appId not match", "WX_PAY_NOTIFICATION_PROCESS_FAILED");
        }
        if (!config.merchantId.equals(notification.merchantId)) {
            throw new WXPayException("appId not match", "WX_PAY_NOTIFICATION_PROCESS_FAILED");
        }
        if (!APIConstants.FEE_TYP_CNY.equals(notification.feeType)) {
            throw new WXPayException("feeType not match", "WX_PAY_NOTIFICATION_PROCESS_FAILED");
        }
        if (notification.totalFee <= 0) {
            throw new WXPayException("missing totalFee", "WX_PAY_NOTIFICATION_PROCESS_FAILED");
        }
    }
}
