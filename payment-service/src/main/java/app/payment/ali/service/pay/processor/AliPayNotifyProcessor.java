package app.payment.ali.service.pay.processor;

import app.payment.api.ali.pay.PayNotifyResponse;

/**
 * @author mort
 */
public interface AliPayNotifyProcessor {
    void process(PayNotifyResponse notify);
}
