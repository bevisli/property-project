package app.payment.api.ali.pay;

import app.payment.api.ali.ErrorView;
import core.framework.api.json.Property;

/**
 * @author mort
 */
public class PagePayRedirectResponse {
    @Property(name = "transaction_id")
    public String transactionId;

    @Property(name = "redirect_url")
    public String redirectURL;

    @Property(name = "error")
    public ErrorView error;
}
