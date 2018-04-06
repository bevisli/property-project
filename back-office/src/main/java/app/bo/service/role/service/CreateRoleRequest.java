package app.bo.service.role.service;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotEmpty;
import core.framework.api.validate.NotNull;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author mort
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CreateRoleRequest {
    @NotNull
    @NotEmpty
    @Length(max = 50)
    @Property(name = "code")
    public String code;

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
