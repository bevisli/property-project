package middleware.alicloud.sms;

import core.framework.api.json.Property;

/**
 * @author mort
 */
public class PaymentNotificationMessage {
    @Property(name = "orderNumber")
    public String orderNumber;
}
