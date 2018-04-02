package app.payment.api.wx.pay;

import core.framework.api.json.Property;

/**
 * @author mort
 */
public class WXNativePayRequest {
    @Property(name = "out_trade_no")
    public String outTradeNo;
    @Property(name = "product_id")
    public String productId;
    @Property(name = "product_name")
    public String productName;
    @Property(name = "total_amount")
    public Double totalAmount;
    @Property(name = "client_ip")
    public String clientIP;
    @Property(name = "attach_param")
    public String attachParam;
}
