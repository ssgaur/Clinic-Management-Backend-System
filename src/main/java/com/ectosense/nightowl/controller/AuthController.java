package com.ectosense.nightowl.controller;

import com.ectosense.nightowl.service.UserService;
import com.ectosense.nightowl.controller.helper.ApiHelper;
import com.ectosense.nightowl.controller.helper.CustomApiResponse;
import com.ectosense.nightowl.data.entity.User;
import com.ectosense.nightowl.data.model.LoginPojo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

/**
 * {@code AuthController} is the Controller to handle Login, Logout, Register.
 * @author  Shailendra Singh
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/")
@Api(value = "Login Register APIs", produces = "application/json", tags = {"Login Register APIs"})
public class AuthController
{
    @Autowired
    UserService userService;

    @RequestMapping(value = "register" ,method = RequestMethod.POST)
    public ResponseEntity register(@Valid @RequestBody User user, BindingResult errors)
    {
        if (errors.hasErrors())
        {
            return ApiHelper.getBindingErrors(errors);
        }

        user.setId(UUID.randomUUID());
        user.setCreatedAt(new Date());
        user.setIsVerified(false);

        User savedUser = userService.addUser(user);
        CustomApiResponse apiResponse = new CustomApiResponse(HttpStatus.CREATED.value(),
                "User has been created successfully.",
                savedUser, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @RequestMapping(value = "login" ,method = RequestMethod.POST)
    public ResponseEntity login(@Valid @RequestBody LoginPojo loginPojo, BindingResult errors)
    {
        CustomApiResponse apiResponse = new CustomApiResponse(HttpStatus.OK.value(), "Login was success.",
                loginPojo, null);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}


