package app.payment.api.ali.query;

import core.framework.api.json.Property;

import java.time.LocalDateTime;

/**
 * @author mort
 */
public class PayTransactionResponse {
    @Property(name = "order_id")
    public String orderId;

    @Property(name = "payment_id")
    public String paymentId;

    @Property(name = "user_id")
    public String userId;

    @Property(name = "total_amount")
    public Double totalAmount;

    @Property(name = "trade_no")
    public String tradeNo;

    @Property(name = "trade_status")
    public String tradeStatus;

    @Property(name = "created_time")
    public LocalDateTime createdTime;

    @Property(name = "completed_time")
    public LocalDateTime completedTime;

}
