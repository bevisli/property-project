package app.bo.service.user.service;

import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author mort
 */
public class SearchUserRequest {
    @Property(name = "phone_number")
    public String phoneNumber;

    @Property(name = "name")
    public String name;

    @Property(name = "status")
    public UserStatusView status;

    @NotNull
    @Property(name = "skip")
    public Integer skip;

    @NotNull
    @Property(name = "limit")
    public Integer limit;
}
