package app.payment.api.ali.pay;

import core.framework.api.json.Property;

/**
 * @author mort
 */
public class PayNotifyResponse {
    @Property(name = "order_id")
    public String orderId;
    @Property(name = "payment_id")
    public String paymentId;
    @Property(name = "total_amount")
    public Double totalAmount;
    @Property(name = "completed")
    public Boolean completed;
}
