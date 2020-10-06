package com.ectosense.nightowl.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class LoginPojo
{
    @NotNull(message = "Email is mandatory.")
    String email;

    @NotNull(message = "Password is mandatory.")
    String password;
}
