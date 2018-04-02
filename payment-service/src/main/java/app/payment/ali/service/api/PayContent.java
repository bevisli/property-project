package app.payment.ali.service.api;

import core.framework.api.json.Property;

/**
 * @author mort
 */
public class PayContent {
    @Property(name = "out_trade_no")
    public String outTradeNo;
    @Property(name = "product_code")
    public String productCode;
    @Property(name = "total_amount")
    public String totalAmount;
    @Property(name = "subject")
    public String subject;
    @Property(name = "body")
    public String body;
    @Property(name = "passback_params")
    public String passBackParams;
    @Property(name = "extend_params")
    public ExtendParams extendParams;

    public static class ExtendParams {
        @Property(name = "sys_service_provider_id")
        public String serviceProviderId;
    }
}
