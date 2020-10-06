package com.ectosense.nightowl.controller;

import com.ectosense.nightowl.data.entity.Doctor;
import com.ectosense.nightowl.service.impl.DoctorServiceImpl;
import com.ectosense.nightowl.service.impl.MappingServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

/**
 * {@code DoctorController} is the  Controller to handle all user related End points.
 * @author  Shailendra Singh
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/v1/doctor")
@Api(value = "Doctor APIs", produces = "application/json", tags = {"Doctor APIs"})
public class DoctorController extends  ApiController
{
    @Autowired
    private DoctorServiceImpl doctorService;

    @Autowired
    private MappingServiceImpl mappingService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_DOCTOR')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addDoctor(@Valid  @RequestBody Doctor doctor, BindingResult errors, Principal principal)
    {
        return new ResponseEntity<>(doctorService.addDoctor(doctor, getUser(principal)), HttpStatus.CREATED);
    }

    @RequestMapping(value = {"/allDoctors"}, method = RequestMethod.GET)
    public ResponseEntity getAllDoctors(Authentication authentication, Principal principal)
    {
        return new ResponseEntity<>(doctorService.getAllDoctors(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_DOCTOR')")
    @RequestMapping(value = {"/{doctorId}/addClinic/{clinicId}"}, method = RequestMethod.POST)
    public ResponseEntity addClinicToDoctor(@RequestBody Object body , @PathVariable UUID doctorId,
                                            @PathVariable UUID clinicId, BindingResult errors, Principal principal)
    {
        return new ResponseEntity<>(mappingService.attachClinicToDoctor(clinicId, doctorId), HttpStatus.CREATED);
    }
}
