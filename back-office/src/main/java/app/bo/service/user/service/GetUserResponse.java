package app.bo.service.user.service;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author mort
 */
public class GetUserResponse {
    @Property(name = "id")
    public String id;

    @Property(name = "phone_number")
    public String phoneNumber;

    @Property(name = "name")
    public String name;

    @Property(name = "status")
    public UserStatusView status;

    @Property(name = "role_codes")
    public List<String> roleCodes;
}
