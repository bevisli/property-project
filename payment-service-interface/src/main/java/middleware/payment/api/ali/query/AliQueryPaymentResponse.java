package middleware.payment.api.ali.query;

import core.framework.api.json.Property;
import middleware.payment.api.ali.ErrorView;

import java.time.LocalDateTime;

/**
 * @author mort
 */
public class AliQueryPaymentResponse {
    @Property(name = "trade_no")
    public String tradeNo;
    @Property(name = "out_trade_no")
    public String outTradeNo;
    @Property(name = "buyer_id")
    public String buyerId;
    @Property(name = "store_id")
    public String storeId;
    @Property(name = "store_name")
    public String storeName;
    @Property(name = "trade_status")
    public String tradeStatus;
    @Property(name = "total_amount")
    public Double totalAmount;
    @Property(name = "buyer_logon_id")
    public String buyerLogonId;
    @Property(name = "buyer_pay_amount")
    public Double buyerPayAmount;
    @Property(name = "buyer_user_id")
    public String buyerUserId;
    @Property(name = "buyer_user_type")
    public String buyerUserType;
    @Property(name = "discount_amount")
    public String discountAmount;
    @Property(name = "open_id")
    public String openId;
    @Property(name = "send_pay_date")
    public LocalDateTime sendPayDate;
    @Property(name = "error")
    public ErrorView error;
}
