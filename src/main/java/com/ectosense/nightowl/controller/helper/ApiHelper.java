package com.ectosense.nightowl.controller.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

public final class ApiHelper
{
    private  ApiHelper()
    {
    }

    public static ResponseEntity getBindingErrors(BindingResult errors)
    {
        List<String> errorList = errors.getAllErrors().stream().map(e -> e.getDefaultMessage())
                .collect(Collectors.toList());
        CustomApiResponse apiResponse = new CustomApiResponse(HttpStatus.BAD_REQUEST.value(),
                "Invalid input.",
                null, errorList);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
