package middleware.payment;

import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import core.framework.module.Module;
import middleware.payment.ali.AliPayConfig;
import middleware.payment.ali.domain.AliPayTransaction;
import middleware.payment.ali.service.AliErrorService;
import middleware.payment.ali.service.SignatureService;
import middleware.payment.ali.service.pay.PagePayService;
import middleware.payment.ali.service.pay.PayNotifyService;
import middleware.payment.ali.service.pay.processor.AliPayNotifyProcessor;
import middleware.payment.ali.service.query.AliQueryService;
import middleware.payment.ali.web.AliPagePayNotifyController;

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
        bind(PagePayService.class);
        bind(PayNotifyService.class);
        bind(AliQueryService.class);

        route().post(ALI_PAY_NOTIFY_URL_PATH, bind(AliPagePayNotifyController.class)::notify);
    }

    public void notificationProcessor(AliPayNotifyProcessor processor) {
        PayNotifyService notificationService = bean(PayNotifyService.class);
        notificationService.processor = processor;
    }
}
