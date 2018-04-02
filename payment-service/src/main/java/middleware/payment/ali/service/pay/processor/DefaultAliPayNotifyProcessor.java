package middleware.payment.ali.service.pay.processor;

import middleware.payment.api.ali.pay.PayNotifyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mort
 */
public class DefaultAliPayNotifyProcessor implements AliPayNotifyProcessor {
    private final Logger logger = LoggerFactory.getLogger(DefaultAliPayNotifyProcessor.class);

    @Override
    public void process(PayNotifyResponse notify) {
        logger.info("receive pay notify from ali");
    }
}
