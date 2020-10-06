package com.ectosense.nightowl.data.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public enum UserRole
{
    ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_DOCTOR("ROLE_DOCTOR"),
    ROLE_CLINIC("ROLE_CLINIC"),
    ROLE_ASSISTANT("ROLE_ASSISTANT"),
    ROLE_PATIENT("ROLE_PATIENT");

    private final String _value;

    UserRole(final String value)
    {
        _value = Objects.requireNonNull(value);
    }

    @JsonValue
    public String getValue()
    {
        return _value;
    }
}
