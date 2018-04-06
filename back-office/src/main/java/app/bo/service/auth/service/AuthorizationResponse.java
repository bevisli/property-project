package app.bo.service.auth.service;

import core.framework.api.json.Property;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.List;

/**
 * @author mort
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AuthorizationResponse {
    @Property(name = "user")
    public User user;

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class User {
        @Property(name = "id")
        public String id;

        @Property(name = "phone_number")
        public String phoneNumber;

        @Property(name = "user_name")
        public String userName;

        @Property(name = "role_codes")
        public List<String> roleCodes;
    }
}
