package app.bo.web.controller.user;

import app.bo.service.user.service.UpdateUserRoleRequest;
import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author mort
 */
public class UpdateRoleWebRequest {
    @Property(name = "code")
    public String code;

    @NotNull
    @Property(name = "role_action")
    public UpdateUserRoleRequest.RoleAction roleAction;
}
