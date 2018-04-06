package app.bo.service.role.domain;

import core.framework.api.validate.Length;
import core.framework.api.validate.NotEmpty;
import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

import java.time.LocalDateTime;

/**
 * @author mort
 */
@Table(name = "roles")
public class Role {
    @PrimaryKey
    @Length(max = 50)
    @Column(name = "code")
    public String code;

    @NotNull
    @NotEmpty
    @Length(max = 50)
    @Column(name = "name")
    public String name;

    @NotNull
    @NotEmpty
    @Length(max = 50)
    @Column(name = "created_by")
    public String createdBy;

    @NotNull
    @Column(name = "created_time")
    public LocalDateTime createdTime;

    @NotNull
    @NotEmpty
    @Length(max = 50)
    @Column(name = "updated_by")
    public String updatedBy;

    @NotNull
    @Column(name = "updated_time")
    public LocalDateTime updatedTime;
}
