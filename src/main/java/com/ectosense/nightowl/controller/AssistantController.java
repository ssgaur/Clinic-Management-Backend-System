package com.ectosense.nightowl.controller;

import com.ectosense.nightowl.data.entity.Assistant;
import com.ectosense.nightowl.service.impl.AssistantServiceImpl;
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
 * {@code AssistantController} is the  Controller to handle all Assistant related End points.
 * @author  Shailendra Singh
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/v1/assistant")
@Api(value = "Assistant APIs", produces = "application/json", tags = {"Assistant APIs"})
public class AssistantController extends ApiController
{
    @Autowired
    private AssistantServiceImpl assistantService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ASSISTANT')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addAssistant(@Valid @RequestBody Assistant assistant, BindingResult errors, Principal principal)
    {
        return new ResponseEntity<>(assistantService.addAssistant(assistant, getUser(principal)), HttpStatus.CREATED);
    }

    @RequestMapping(value = {"/allAssistant"}, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity getAllAssistant(Authentication authentication, Principal principal)
    {
        return new ResponseEntity<>(assistantService.getAllAssistant(), HttpStatus.OK);
    }

    @RequestMapping(value = {"/{assistantId}"}, method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity getAssistantById(@PathVariable UUID assistantId, Authentication authentication, Principal principal)
    {
        return new ResponseEntity<>(assistantService.getAssistantById(assistantId), HttpStatus.OK);
    }
}
