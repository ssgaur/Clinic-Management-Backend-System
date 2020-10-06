package com.ectosense.nightowl.utils;

import com.ectosense.nightowl.data.entity.User;
import com.ectosense.nightowl.data.enums.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Utility
{
    private static final List<UserRole> allowedRoles = Stream.of(UserRole.values()).collect(Collectors.toList());

    private Utility() {}

    public static List<UserRole> validateRole(User user)
    {
        if (user == null || user.getUserMeta() == null)
        {
            throw new IllegalArgumentException("INVALID_ROLE");
        }
        List<UserRole> roleList = user.getUserMeta().getUserRoles();
        if (roleList.size() == 0 )
        {
            throw new IllegalArgumentException("INVALID_ROLE");
        }
        List<UserRole> verifiedRoles = new ArrayList<>();
        for (UserRole role: roleList)
        {
            if (!allowedRoles.contains(role))
            {
                throw new IllegalArgumentException("INVALID_ROLE");
            }
            verifiedRoles.add(role);
        }
        return verifiedRoles;
    }
}
