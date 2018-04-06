package app.bo.web.controller.user;

import app.bo.service.user.service.UserStatusView;
import core.framework.api.json.Property;
import core.framework.api.validate.NotNull;

/**
 * @author mort
 */
public class UpdateUserStatusWebRequest {
    @NotNull
    @Property(name = "status")
    public UserStatusView status;
}
