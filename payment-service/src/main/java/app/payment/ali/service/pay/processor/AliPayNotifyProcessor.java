package app.payment.ali.service.pay.processor;

import app.payment.api.ali.pay.AliPayNotifyMessage;

/**
 * @author mort
 */
public interface AliPayNotifyProcessor {
    void process(AliPayNotifyMessage notify);
}
