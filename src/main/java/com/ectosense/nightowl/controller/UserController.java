package com.ectosense.nightowl.controller;

import com.ectosense.nightowl.controller.helper.CustomApiResponse;
import com.ectosense.nightowl.service.UserService;
import com.ectosense.nightowl.data.entity.User;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import java.security.Principal;
import java.util.UUID;

/**
 * {@code UserController} is the  Controller to handle all user related End points.
 * @author  Shailendra Singh
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@Api(value = "User APIs", produces = "application/json", tags = {"User APIs"})
public class UserController
{
    @Autowired
    UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = {"/{userId}"}, method = RequestMethod.GET)
    public ResponseEntity getUserById(@PathVariable UUID userId)
    {
        User user = userService.getById(userId);
        CustomApiResponse apiResponse = new CustomApiResponse(HttpStatus.OK.value(),
                "Success",
                user, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @RequestMapping(value = {"/allUsers"}, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity getAllUsers(Authentication authentication, Principal principal)
    {
        Page<User> users = userService.getAllUsers();
        CustomApiResponse apiResponse = new CustomApiResponse(HttpStatus.OK.value(),
                "Success",
                users, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
