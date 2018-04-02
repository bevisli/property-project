package middleware.alicloud.sms;

import core.framework.api.json.Property;

/**
 * @author mort
 */
public class SecurityCodeMessage {
    @Property(name = "code")
    public String code;
}
