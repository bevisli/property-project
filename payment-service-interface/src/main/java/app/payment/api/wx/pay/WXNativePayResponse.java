package app.payment.api.wx.pay;

import core.framework.api.json.Property;

/**
 * @author mort
 */
public class WXNativePayResponse {
    @Property(name = "prepay_id")
    public String prepayId;

    @Property(name = "code_url")
    public String codeURL;

    @Property(name = "error")
    public Error error;

    public static class Error {
        @Property(name = "code")
        public String code;
        @Property(name = "message")
        public String message;
    }
}
