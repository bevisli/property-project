package app.bo.service.auth.service;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotEmpty;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Pattern;

/**
 * @author mort
 */
public class AuthorizationRequest {
    @NotNull
    @NotEmpty
    @Pattern("[0-9]*")
    @Length(min = 11, max = 11)
    @Property(name = "phone_number")
    public String phoneNumber;

    @NotNull
    @NotEmpty
    @Length(min = 6)
    @Property(name = "password")
    public String password;
}
