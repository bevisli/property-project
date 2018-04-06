package app.bo.service.role.service;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotEmpty;
import core.framework.api.validate.NotNull;

/**
 * @author mort
 */
public class UpdateRoleRequest {
    @NotNull
    @NotEmpty
    @Length(max = 50)
    @Property(name = "name")
    public String name;

    @NotNull
    @NotEmpty
    @Length(max = 50)
    @Property(name = "requested_by")
    public String requestedBy;
}
