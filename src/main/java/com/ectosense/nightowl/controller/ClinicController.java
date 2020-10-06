package com.ectosense.nightowl.controller;

import com.ectosense.nightowl.data.entity.Clinic;
import com.ectosense.nightowl.service.impl.ClinicServiceImpl;
import com.ectosense.nightowl.service.impl.MappingServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
 * {@code ClinicController} is the  Controller to handle all user related End points.
 * @author  Shailendra Singh
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/v1/clinic")
@Api(value = "Clinic APIs", produces = "application/json", tags = {"Clinic APIs"})
public class ClinicController extends ApiController
{
    @Autowired
    private ClinicServiceImpl clinicService;

    @Autowired
    private MappingServiceImpl mappingService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_CLINIC')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addClinic(@Valid @RequestBody Clinic clinic, BindingResult errors, Principal principal)
    {
        return new ResponseEntity<>(clinicService.addClinic(clinic, getUser(principal)), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_CLINIC')")
    @RequestMapping(value = {"/{clinicId}/addDoctor/{doctorId}"}, method = RequestMethod.POST)
    public ResponseEntity addDoctorToClinic(
            @RequestBody Object body , @PathVariable UUID clinicId, @PathVariable UUID doctorId, BindingResult errors,
            Principal principal
    )
    {
        return new ResponseEntity<>(mappingService.attachDoctorToClinic(clinicId, doctorId), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_CLINIC')")
    @RequestMapping(value = {"/{clinicId}/addAssistant/{assistantId}"}, method = RequestMethod.POST)
    public ResponseEntity addAssistantToClinic(
            @RequestBody Object body , @PathVariable UUID clinicId, @PathVariable UUID assistantId, BindingResult errors,
            Principal principal
    )
    {
        return new ResponseEntity<>(mappingService.attachAssistantToClinic(clinicId, assistantId), HttpStatus.CREATED);
    }

    @RequestMapping(value = {"/{clinicId}/assistants"}, method = RequestMethod.GET)
    public ResponseEntity getClinicsAssistants(@PathVariable UUID clinicId, Authentication authentication,
                                               Principal principal)
    {
        return new ResponseEntity<>(clinicService.getAllAssistants(clinicId), HttpStatus.OK);
    }

    @RequestMapping(value = {"/{clinicId}"}, method = RequestMethod.GET)
    public ResponseEntity getClinicById(@PathVariable UUID clinicId, Authentication authentication, Principal principal)
    {
        return new ResponseEntity<>(clinicService.getClinicById(clinicId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = {"/allClinics"}, method = RequestMethod.GET)
    public ResponseEntity getAllClinics(Authentication authentication, Principal principal)
    {
        return new ResponseEntity<>(clinicService.getAllClinic(), HttpStatus.OK);
    }
}
