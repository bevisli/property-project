package app.bo.service.user.service;

import core.framework.api.json.Property;

/**
 * @author mort
 */
public enum UserStatusView {
    @Property(name = "ACTIVE")
    ACTIVE,
    @Property(name = "INACTIVE")
    INACTIVE
}
