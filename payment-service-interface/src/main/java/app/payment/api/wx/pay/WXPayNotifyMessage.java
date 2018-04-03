package app.payment.api.wx.pay;

import core.framework.api.json.Property;
import core.framework.api.validate.NotEmpty;
import core.framework.api.validate.NotNull;

/**
 * @author mort
 */
public class WXPayNotifyMessage {
    @NotNull
    @NotEmpty
    @Property(name = "order_id")
    public String orderId;

    @NotNull
    @NotEmpty
    @Property(name = "payment_id")
    public String paymentId;

    @NotNull
    @Property(name = "total_amount")
    public Double totalAmount;

    @NotNull
    @Property(name = "completed")
    public Boolean completed;
}
