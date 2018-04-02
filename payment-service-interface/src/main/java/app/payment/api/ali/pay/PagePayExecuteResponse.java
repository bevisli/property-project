package app.payment.api.ali.pay;

import app.payment.api.ali.ErrorView;
import core.framework.api.json.Property;

/**
 * @author mort
 */
public class PagePayExecuteResponse {
    @Property(name = "transaction_id")
    public String transactionId;

    @Property(name = "body")
    public String body;

    @Property(name = "error")
    public ErrorView error;
}
