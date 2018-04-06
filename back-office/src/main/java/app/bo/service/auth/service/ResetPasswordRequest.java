package app.bo.service.auth.service;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.Min;
import core.framework.api.validate.NotEmpty;
import core.framework.api.validate.NotNull;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author mort
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ResetPasswordRequest {
    @NotNull
    @NotEmpty
    @Property(name = "old_password")
    public String oldPassword;

    @NotNull
    @NotEmpty
    @Min(6)
    @Property(name = "password")
    public String password;

    @NotNull
    @NotEmpty
    @Length(max = 50)
    @Property(name = "requested_by")
    public String requestedBy;
}
