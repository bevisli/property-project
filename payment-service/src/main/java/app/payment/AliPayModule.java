package app.payment;

import app.payment.ali.AliPayConfig;
import app.payment.ali.domain.AliPayTransaction;
import app.payment.ali.service.AliErrorService;
import app.payment.ali.service.SignatureService;
import app.payment.ali.service.job.SyncPaymentsJob;
import app.payment.ali.service.pay.PagePayService;
import app.payment.ali.service.pay.PayNotifyService;
import app.payment.ali.service.pay.PayTransactionService;
import app.payment.ali.service.pay.processor.AliPayNotifyProcessor;
import app.payment.ali.service.pay.processor.KafkaNotifyProcessor;
import app.payment.ali.service.query.PayQueryService;
import app.payment.ali.web.AliPayWebServiceImpl;
import app.payment.ali.web.NotifyController;
import app.payment.api.AliPayWebService;
import app.payment.api.ali.pay.AliPayNotifyMessage;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import core.framework.module.Module;

import java.time.Duration;

/**
 * @author mort
 */
public class AliPayModule extends Module {
    public static final String ALI_PAY_NOTIFY_URL_PATH = "/ali-pay/notify";

    @Override
    protected void initialize() {
        loadProperties("alipay.properties");
        db().repository(AliPayTransaction.class);

        AliPayConfig config = new AliPayConfig();
        config.appId = requiredProperty("ali.pay.appId");
        config.appPrivateKey = requiredProperty("ali.pay.appPrivateKey");
        config.publicKey = requiredProperty("ali.pay.publicKey");
        config.providerId = property("ali.pay.providerId").orElse(null);
        config.sellerId = property("ali.pay.sellerId").orElse(null);
        config.gateway = requiredProperty("ali.pay.gateway");
        config.serviceHost = requiredProperty("ali.pay.serviceHost");
        config.kafkaNotify = Boolean.valueOf(requiredProperty("ali.pay.kafkaNotify"));
        bind(config);

        AlipayClient alipayClient = new DefaultAlipayClient(config.gateway,
            config.appId,
            config.appPrivateKey,
            AlipayConstants.FORMAT_JSON,
            AlipayConstants.CHARSET_UTF8,
            config.publicKey,
            AlipayConstants.SIGN_TYPE_RSA2);
        bind(AlipayClient.class, alipayClient);

        bind(SignatureService.class);
        bind(AliErrorService.class);
        bind(PayQueryService.class);
        bind(PayTransactionService.class);
        bind(PagePayService.class);
        bind(PayNotifyService.class);
        if (config.kafkaNotify) {
            kafka().publish("ali-pay-notify", AliPayNotifyMessage.class);
            bean(PayNotifyService.class).processor = bind(KafkaNotifyProcessor.class);
        }

        route().post(ALI_PAY_NOTIFY_URL_PATH, bind(NotifyController.class)::notify);
        schedule().fixedRate("sync-ali-payments-job", bind(SyncPaymentsJob.class), Duration.ofHours(1));
        api().service(AliPayWebService.class, bind(AliPayWebServiceImpl.class));
    }

    public void notificationProcessor(AliPayNotifyProcessor processor) {
        PayNotifyService notificationService = bean(PayNotifyService.class);
        notificationService.processor = processor;
    }
}
