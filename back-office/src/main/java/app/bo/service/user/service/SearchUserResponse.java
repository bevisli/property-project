package app.bo.service.user.service;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author mort
 */
public class SearchUserResponse {
    @Property(name = "total")
    public Integer total;

    @Property(name = "users")
    public List<User> users;

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

}
