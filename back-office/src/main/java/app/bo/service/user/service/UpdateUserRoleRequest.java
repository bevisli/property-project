package app.bo.service.user.service;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotEmpty;
import core.framework.api.validate.NotNull;

import javax.xml.bind.annotation.XmlEnumValue;

/**
 * @author mort
 */
public class UpdateUserRoleRequest {
    @NotNull
    @NotEmpty
    @Property(name = "role_code")
    public String roleCode;

    @NotNull
    @Property(name = "action")
    public RoleAction action;

    @NotNull
    @NotEmpty
    @Length(max = 50)
    @Property(name = "requested_by")
    public String requestedBy;

    public enum RoleAction {
        @XmlEnumValue("ASSIGN")
        ASSIGN,
        @XmlEnumValue("UNASSIGN")
        UNASSIGN
    }
}
