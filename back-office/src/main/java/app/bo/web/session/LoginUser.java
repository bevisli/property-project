package app.bo.web.session;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author mort
 */
public class LoginUser {
    @Property(name = "id")
    public String id;

    @Property(name = "phone_number")
    public String phoneNumber;

    @Property(name = "user_name")
    public String userName;

    @Property(name = "role_codes")
    public List<String> roleCodes;
}
