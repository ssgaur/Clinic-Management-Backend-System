package com.ectosense.nightowl.service;

import com.ectosense.nightowl.data.entity.User;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserService
{
    User addUser(User user);

    User getByEmail(String email);

    User getById(UUID userId);

    User getByPhone(String phone);

    Page getAllUsers();
}
