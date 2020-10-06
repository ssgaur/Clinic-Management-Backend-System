package com.ectosense.nightowl.controller;

import com.ectosense.nightowl.data.entity.User;
import com.ectosense.nightowl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import java.security.Principal;

public class ApiController
{
    @Autowired
    private UserService userService;

    public User getUser(Principal principal)
    {
        User user = userService.getByEmail(principal.getName());
        if (user == null)
        {
            throw new AccessDeniedException("INVALID_USER");
        }
        return user;
    }
}
