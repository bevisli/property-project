package app.payment;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import core.framework.module.Module;
import app.payment.wx.WXPayConfigImpl;
import app.payment.wx.service.WXSignatureService;
import app.payment.wx.service.pay.WXNativePayService;
import app.payment.wx.service.pay.WXPayNotificationService;
import app.payment.wx.service.pay.processor.WXPayNotificationProcessor;
import app.payment.wx.service.query.WXQueryService;
import app.payment.wx.web.WXNativePayNotifyController;

/**
 * @author mort
 */
public class WXPayModule extends Module {
    public static final String WX_PAY_NOTIFY_URL_PATH = "/wx-pay/notify";

    @Override
    protected void initialize() {
        loadProperties("wxpay.properties");

        WXPayConfigImpl config = new WXPayConfigImpl();
        config.appId = requiredProperty("wei.pay.appId");
        config.appSecretKey = property("wei.pay.appSecretKey").orElse(null);
        config.merchantId = requiredProperty("wei.pay.merchantId");
        config.apiPrivateKey = requiredProperty("wei.pay.apiPrivateKey");
        config.certPath = property("wei.pay.certPath").orElse(null);
        config.ossImageBucket = requiredProperty("wei.pay.ossImageBucket");
        config.serviceHost = requiredProperty("wei.pay.serviceHost");
        config.signType = WXPayConstants.SignType.MD5;
        config.sandbox = Boolean.valueOf(requiredProperty("wei.pay.sandbox"));

        bind(config);

        bind(new WXPay(config, config.signType, config.sandbox));
        bind(WXSignatureService.class);
        bind(WXNativePayService.class);
        bind(WXPayNotificationService.class);
        bind(WXQueryService.class);

        route().post(WX_PAY_NOTIFY_URL_PATH, bind(WXNativePayNotifyController.class)::notify);
    }

    public void notificationProcessor(WXPayNotificationProcessor processor) {
        WXPayNotificationService notificationService = bean(WXPayNotificationService.class);
        notificationService.processor = processor;
    }
}
