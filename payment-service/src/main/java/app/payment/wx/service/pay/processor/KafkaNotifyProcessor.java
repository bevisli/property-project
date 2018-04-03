package app.payment.wx.service.pay.processor;

import app.payment.api.wx.pay.WXPayNotifyMessage;
import core.framework.inject.Inject;
import core.framework.kafka.MessagePublisher;

/**
 * @author mort
 */
public class KafkaNotifyProcessor implements WXPayNotifyProcessor {
    @Inject
    MessagePublisher<WXPayNotifyMessage> payNotifyMessagePublisher;

    @Override
    public void process(WXPayNotifyMessage message) {
        payNotifyMessagePublisher.publish(message);
    }
}
