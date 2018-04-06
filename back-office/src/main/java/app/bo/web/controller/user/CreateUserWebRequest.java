package app.bo.web.controller.user;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotEmpty;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Pattern;

/**
 * @author mort
 */
public class CreateUserWebRequest {
    @NotNull
    @NotEmpty
    @Pattern("[0-9]*")
    @Length(min = 11, max = 11)
    @Property(name = "phone_number")
    public String phoneNumber;

    @NotNull
    @NotEmpty
    @Pattern("[a-zA-Z0-9]*")
    @Length(min = 6, max = 50)
    @Property(name = "password")
    public String password;

    @NotNull
    @NotEmpty
    @Length(max = 50)
    @Property(name = "name")
    public String name;
}
