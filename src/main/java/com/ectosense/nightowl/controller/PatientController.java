package com.ectosense.nightowl.controller;

import com.ectosense.nightowl.data.entity.Patient;
import com.ectosense.nightowl.service.impl.PatientServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

/**
 * {@code PatientController} is the  Controller to handle all Patient related End points.
 * @author  Shailendra Singh
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/v1/patient")
@Api(value = "Patient APIs", produces = "application/json", tags = {"Patient APIs"})
public class PatientController extends ApiController
{
    @Autowired
    private PatientServiceImpl patientService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN') " +
            "or hasRole('ROLE_PATIENT') or  hasRole('ROLE_ASSISTANT')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addPatient(@Valid @RequestBody Patient patient, BindingResult errors, Principal principal)
    {
        return new ResponseEntity<>(patientService.addPatient(patient, getUser(principal)), HttpStatus.CREATED);
    }

    @RequestMapping(value = {"/allPatient"}, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity getAllPatients(Authentication authentication, Principal principal)
    {
        return new ResponseEntity<>(patientService.getAllPatient(), HttpStatus.OK);
    }
}
