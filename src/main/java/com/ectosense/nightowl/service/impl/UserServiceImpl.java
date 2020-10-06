package com.ectosense.nightowl.service.impl;

import com.ectosense.nightowl.data.entity.User;
import com.ectosense.nightowl.data.enums.UserRole;
import com.ectosense.nightowl.data.repository.UserRepository;
import com.ectosense.nightowl.exception.ResourceExistException;
import com.ectosense.nightowl.exception.ResourceNotFoundException;
import com.ectosense.nightowl.service.UserService;
import com.ectosense.nightowl.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User addUser(User user)
    {
        List<UserRole> verifiedRoles =  Utility.validateRole(user);
        user.getUserMeta().setUserRoles(verifiedRoles);
        if (userRepository.existsByEmail(user.getEmail()) ||
                userRepository.existsByphoneNumber(user.getPhoneNumber()))
        {
            throw new ResourceExistException("User already exists with given Phone or Email. !!");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getByEmail(String email)
    {
        Objects.nonNull(email);
        return userRepository.findByEmail(email).orElseThrow(
                () ->  new ResourceNotFoundException("User not found with given Email."));
    }

    @Override
    public User getByPhone(String phone)
    {
        Objects.nonNull(phone);
        return userRepository.findByPhoneNumber(phone).orElseThrow(
                () ->  new ResourceNotFoundException("User not found with given Phone."));
    }

    @Override
    public  User getById(UUID userId)
    {
        Objects.nonNull(userId);
        User user =  userRepository.getOne(userId);
        Objects.nonNull(user);
        return user;
    }

    @Override
    public Page<User> getAllUsers()
    {
        PageRequest pageableRequest = PageRequest.of(0, 100, Sort.by("createdAt").descending());
        Page<User> users =  userRepository.findAll(pageableRequest);
        return users;
    }
}
