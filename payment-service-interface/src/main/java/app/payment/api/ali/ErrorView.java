package app.payment.api.ali;

import core.framework.api.json.Property;

/**
 * @author mort
 */
public class ErrorView {
    @Property(name = "error_code")
    public String errorCode;
    @Property(name = "error_message")
    public String errorMessage;
}
