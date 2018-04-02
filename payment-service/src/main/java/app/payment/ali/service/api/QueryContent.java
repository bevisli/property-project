package app.payment.ali.service.api;

import core.framework.api.json.Property;

/**
 * @author mort
 */
public class QueryContent {
    @Property(name = "out_trade_no")
    public String outTradeNo;
    @Property(name = "trade_no")
    public String tradeNo;
}
