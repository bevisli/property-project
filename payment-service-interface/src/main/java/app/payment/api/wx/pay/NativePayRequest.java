package app.payment.api.wx.pay;

import core.framework.api.json.Property;

/**
 * @author mort
 */
public class NativePayRequest {
    @Property(name = "order_id")
    public String orderId;

    @Property(name = "payment_id")
    public String paymentId;

    @Property(name = "user_id")
    public String userId;

    @Property(name = "total_amount")
    public Double totalAmount;

    @Property(name = "product_name")
    public String productName;

    @Property(name = "client_ip")
    public String clientIP;

    @Property(name = "requested_by")
    public String requestedBy;
}
