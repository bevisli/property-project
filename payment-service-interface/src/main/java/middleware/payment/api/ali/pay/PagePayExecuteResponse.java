package middleware.payment.api.ali.pay;

import core.framework.api.json.Property;
import middleware.payment.api.ali.ErrorView;

/**
 * @author mort
 */
public class PagePayExecuteResponse {
    @Property(name = "body")
    public String body;
    @Property(name = "error")
    public ErrorView error;
}
