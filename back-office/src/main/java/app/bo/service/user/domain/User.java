package app.bo.service.user.domain;

import core.framework.api.validate.Length;
import core.framework.api.validate.NotEmpty;
import core.framework.api.validate.NotNull;
import core.framework.api.validate.Pattern;
import core.framework.db.Column;
import core.framework.db.DBEnumValue;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

import java.time.LocalDateTime;

/**
 * @author mort
 */
@Table(name = "users")
public class User {
    @PrimaryKey
    @Column(name = "id")
    public String id;

    @NotNull
    @NotEmpty
    @Pattern("[0-9]*")
    @Length(min = 11, max = 11)
    @Column(name = "phone_number")
    public String phoneNumber;

    @NotNull
    @NotEmpty
    @Length(min = 6, max = 50)
    @Column(name = "password")
    public String password;

    @NotNull
    @NotEmpty
    @Length(max = 50)
    @Column(name = "name")
    public String name;

    @NotNull
    @Column(name = "status")
    public Status status;

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

    public enum Status {
        @DBEnumValue("ACTIVE")
        ACTIVE,
        @DBEnumValue("INACTIVE")
        INACTIVE,
    }
}
