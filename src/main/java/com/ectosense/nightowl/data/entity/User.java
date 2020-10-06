package com.ectosense.nightowl.data.entity;

import com.ectosense.nightowl.data.model.UserMeta;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "tbl_user", schema = "public")
@Slf4j
public class User
{
    @ApiModelProperty(hidden = true)
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "id", unique = true, nullable = false)
    @Type(type="pg-uuid")
    private UUID id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull(message = "First Name is mandatory.")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "Last Name is mandatory.")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "Gender is mandatory.")
    @Column(name = "gender")
    private String gender;

    @NotNull(message = "Email is mandatory.")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Password is mandatory.")
    @Column(name = "password")
    private String password;

    @Column(name = "user_meta")
    @Type(type = "com.ectosense.nightowl.config.JSONBUserType", parameters = {@Parameter(name = "className",
            value = "com.ectosense.nightowl.data.model.UserMeta")})
    private UserMeta userMeta;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @ApiModelProperty(hidden = true)
    @Column(name = "created_at")
    private Date createdAt;

    @ApiModelProperty(hidden = true)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    @Override
    public boolean equals(Object obj) {
        if ((null == obj) || (obj.getClass() != User.class))
            return false;
        User user = (User)obj;
        return id.equals(user.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
