package app.payment;

import app.payment.api.WXPayWebService;
import app.payment.api.wx.pay.WXPayNotifyMessage;
import app.payment.wx.WXPayConfigImpl;
import app.payment.wx.domain.WXPayTransaction;
import app.payment.wx.service.SignatureService;
import app.payment.wx.service.job.SyncPaymentsJob;
import app.payment.wx.service.pay.NativePayService;
import app.payment.wx.service.pay.PayNotifyService;
import app.payment.wx.service.pay.PayTransactionService;
import app.payment.wx.service.pay.processor.KafkaNotifyProcessor;
import app.payment.wx.service.pay.processor.WXPayNotifyProcessor;
import app.payment.wx.service.query.PaymentQueryService;
import app.payment.wx.web.NativePayNotifyController;
import app.payment.wx.web.WXPayWebServiceImpl;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import core.framework.module.Module;

import java.time.Duration;

/**
 * @author mort
 */
public class WXPayModule extends Module {
    public static final String WX_PAY_NOTIFY_URL_PATH = "/wx-pay/notify";

    @Override
    protected void initialize() {
        loadProperties("wxpay.properties");
        db().repository(WXPayTransaction.class);

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
        config.kafkaNotify = Boolean.valueOf(requiredProperty("wei.pay.kafkaNotify"));

        bind(config);

        bind(new WXPay(config, config.signType, config.sandbox));
        bind(SignatureService.class);
        bind(PaymentQueryService.class);
        bind(PayTransactionService.class);
        bind(NativePayService.class);
        bind(PayNotifyService.class);
        if (config.kafkaNotify) {
            kafka().publish("wx-pay-notify", WXPayNotifyMessage.class);
            bean(PayNotifyService.class).processor = bind(KafkaNotifyProcessor.class);
        }

        route().post(WX_PAY_NOTIFY_URL_PATH, bind(NativePayNotifyController.class)::notify);
        schedule().fixedRate("sync-wx-payments-job", bind(SyncPaymentsJob.class), Duration.ofHours(1));
        api().service(WXPayWebService.class, bind(WXPayWebServiceImpl.class));
    }

    public void notificationProcessor(WXPayNotifyProcessor processor) {
        PayNotifyService notificationService = bean(PayNotifyService.class);
        notificationService.processor = processor;
    }
}
