package middleware.payment.ali.service.pay.processor;

import middleware.payment.api.ali.pay.PayNotifyResponse;

/**
 * @author mort
 */
public interface AliPayNotifyProcessor {
    void process(PayNotifyResponse notify);
}
