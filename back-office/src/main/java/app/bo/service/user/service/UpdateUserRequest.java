package app.bo.service.user.service;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotEmpty;
import core.framework.api.validate.NotNull;

/**
 * @author mort
 */
public class UpdateUserRequest {
    @NotEmpty
    @NotNull
    @Length(max = 50)
    @Property(name = "name")
    public String name;

    @NotNull
    @Property(name = "status")
    public UserStatusView status;

    @NotNull
    @NotEmpty
    @Length(max = 50)
    @Property(name = "requested_by")
    public String requestedBy;
}