package app.bo.service.role.service;

import core.framework.api.json.Property;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author mort
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RoleView {
    @Property(name = "code")
    public String code;

    @Property(name = "name")
    public String name;
}
