package com.ectosense.nightowl.data.model;

import com.ectosense.nightowl.data.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMeta implements Serializable
{
    static final long serialVersionUID = -3335270842585067351L;

    private List<UserRole>  userRoles;
    private String          authProvider;
    private String          profileImage;
}
