package app.bo.web.controller.role;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;
import core.framework.api.validate.NotEmpty;
import core.framework.api.validate.NotNull;

/**
 * @author mort
 */
public class CreateRoleWebRequest {
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
}
