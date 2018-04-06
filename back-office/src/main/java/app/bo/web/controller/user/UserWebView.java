package app.bo.web.controller.user;

import app.bo.service.user.service.UserStatusView;
import core.framework.api.json.Property;

import java.util.List;

/**
 * @author mort
 */
public class UserWebView {
    @Property(name = "user")
    public User user;

    @Property(name = "role")
    public List<Role> roles;

    public static class User {
        @Property(name = "id")
        public String id;

        @Property(name = "phone_number")
        public String phoneNumber;

        @Property(name = "name")
        public String name;

        @Property(name = "status")
        public UserStatusView status;
    }

    public static class Role {
        @Property(name = "code")
        public String code;

        @Property(name = "name")
        public String name;

        @Property(name = "has_role")
        public Boolean hasRole;
    }
}
