package app.payment.ali.service.pay.processor;

import app.payment.api.ali.pay.AliPayNotifyMessage;
import core.framework.inject.Inject;
import core.framework.kafka.MessagePublisher;

/**
 * @author mort
 */
public class KafkaNotifyProcessor implements AliPayNotifyProcessor {
    @Inject
    MessagePublisher<AliPayNotifyMessage> payNotifyMessagePublisher;

    @Override
    public void process(AliPayNotifyMessage notify) {
        payNotifyMessagePublisher.publish(notify);
    }
}
