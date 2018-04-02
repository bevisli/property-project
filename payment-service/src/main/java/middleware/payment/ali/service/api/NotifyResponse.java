package middleware.payment.ali.service.api;

import core.framework.api.json.Property;

import java.time.LocalDateTime;

/**
 * @author mort
 */
public class NotifyResponse {
    @Property(name = "notify_id")
    public String notifyId;
    @Property(name = "notify_time")
    public String notifyTime;
    @Property(name = "notify_type")
    public String notifyType;
    @Property(name = "trade_no")
    public String tradeNo;
    @Property(name = "app_id")
    public String appId;
    @Property(name = "out_trade_no")
    public String outTradeNo;
    @Property(name = "buyer_id")
    public String buyerId;
    @Property(name = "seller_id")
    public String sellerId;
    @Property(name = "trade_status")
    public String tradeStatus;
    @Property(name = "total_amount")
    public Double totalAmount;
    @Property(name = "subject")
    public String subject;
    @Property(name = "body")
    public String body;
    @Property(name = "gmt_create")
    public LocalDateTime gmtCreate;
    @Property(name = "gmt_payment")
    public LocalDateTime gmtPayment;
    @Property(name = "gmt_close")
    public LocalDateTime gmtClose;
    @Property(name = "passback_params")
    public String passBackParams;
    @Property(name = "completed")
    public Boolean completed;
}
