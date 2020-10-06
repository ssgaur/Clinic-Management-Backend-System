package com.ectosense.nightowl.controller;

import com.ectosense.nightowl.data.entity.Appointment;
import com.ectosense.nightowl.data.entity.FileInfo;
import com.ectosense.nightowl.service.impl.AppointmentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

/**
 * {@code AppointmentController} is the  Controller to handle all Appointment related End points.
 * @author  Shailendra Singh
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController extends ApiController
{
    @Autowired
    private AppointmentServiceImpl appointmentService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_ASSISTANT')")
    @RequestMapping(value = "/clinic/{clinicId}/doctor/{doctorId}/patient/{patientId}", method = RequestMethod.POST)
    public ResponseEntity addAppointment(@Valid @RequestBody Appointment appointment, @PathVariable UUID clinicId,
                                         @PathVariable UUID doctorId, @PathVariable UUID patientId,
                                         BindingResult errors, Principal principal)
    {
        Appointment savedAppointment = appointmentService.createAppointment(
                clinicId, doctorId, patientId, getUser(principal), appointment);
        return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_DOCTOR')" +
            " or hasRole('ROLE_ASSISTANT') or hasRole('ROLE_PATIENT')")
    @RequestMapping(value = "/{appointmentId}", method = RequestMethod.GET)
    public ResponseEntity getAppointmentById(@PathVariable UUID appointmentId, Principal principal)
    {
        return new ResponseEntity<>(appointmentService.getAppointmentById(appointmentId), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN') or hasRole('ROLE_PATIENT')")
    @RequestMapping(value = "/{appointmentId}/upload", method = RequestMethod.POST)
    public ResponseEntity uploadFile(@PathVariable UUID appointmentId, @RequestParam("file") MultipartFile file)
    {
        try {
            FileInfo fileInfo = appointmentService.saveDocumemt(appointmentId, file);
            return new ResponseEntity<>(fileInfo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not Upload.", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = "{appointmentId}/documents", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN') " +
            "or hasRole('ROLE_DOCTOR') or hasRole('ROLE_CLINIC')")
    public ResponseEntity getDocuments(@PathVariable UUID appointmentId)
    {
        List<FileInfo> files = appointmentService.getDocumentsByAppointment(appointmentId);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @RequestMapping(value = "{appointmentId}/files/{filename:.+}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN') " +
            "or hasRole('ROLE_DOCTOR') or hasRole('ROLE_CLINIC') or hasRole('ROLE_PATIENT')")
    public ResponseEntity getDocument(@PathVariable UUID appointmentId, @PathVariable String filename)
    {
        Resource file = appointmentService.getDocument(appointmentId, filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
