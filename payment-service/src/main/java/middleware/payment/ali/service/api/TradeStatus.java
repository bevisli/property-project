package middleware.payment.ali.service.api;

import core.framework.api.json.Property;

/**
 * @author mort
 */
public enum TradeStatus {
    @Property(name = "WAIT_BUYER_PAY")
    WAIT_BUYER_PAY,
    @Property(name = "TRADE_CLOSED")
    TRADE_CLOSED,
    @Property(name = "TRADE_SUCCESS")
    TRADE_SUCCESS,
    @Property(name = "TRADE_FINISHED")
    TRADE_FINISHED
}
