package app.bo.service.role.service;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author mort
 */
public class SearchRoleResponse {
    @Property(name = "total")
    public Integer total;

    @Property(name = "roles")
    public List<RoleView> roles;
}
