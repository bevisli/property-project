package app.bo.service.role.service;

import core.framework.api.json.Property;
import core.framework.api.validate.Length;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author mort
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchRoleRequest {
    @Length(max = 50)
    @Property(name = "code")
    public String code;

    @Length(max = 50)
    @Property(name = "name")
    public String name;

    @Property(name = "skip")
    public Integer skip;

    @Property(name = "limit")
    public Integer limit;
}
