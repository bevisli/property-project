package app.payment.api.ali.pay;

import core.framework.api.json.Property;

/**
 * @author mort
 */
public class PagePayRequest {
    @Property(name = "order_id")
    public String orderId;
    @Property(name = "payment_id")
    public String paymentId;
    @Property(name = "user_id")
    public String userId;
    @Property(name = "amount")
    public Double amount;
    @Property(name = "product_name")
    public String productName;
    @Property(name = "description")
    public String description;
    @Property(name = "return_url")
    public String returnURL;
    @Property(name = "requested_by")
    public String requestedBy;
}
