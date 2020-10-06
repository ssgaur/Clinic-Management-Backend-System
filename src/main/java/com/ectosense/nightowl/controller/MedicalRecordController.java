package com.ectosense.nightowl.controller;


import com.ectosense.nightowl.service.impl.MedicalRecordServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

/**
 * {@code MedicalRecordController} is the  Controller to handle all Medical Document related End points.
 * @author  Shailendra Singh
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/v1/documents")
public class MedicalRecordController extends ApiController
{
    @Autowired
    private MedicalRecordServiceImpl medicalRecordService;


    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file, Principal principal)
    {
        try {
            com.ectosense.nightowl.data.entity.FileInfo fileInfo =
                    medicalRecordService.saveDocument(getUser(principal), file);
            return new ResponseEntity<>(fileInfo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not Upload.", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @RequestMapping(value = "/{clinicId}/upload", method = RequestMethod.POST)
    public ResponseEntity uploadFileForClinic(@PathVariable UUID clinicId,
                                              @RequestParam("file") MultipartFile file, Principal principal)
    {
        try {
            com.ectosense.nightowl.data.entity.FileInfo fileInfo = medicalRecordService.saveDocumentForClinic(clinicId,
                    file, getUser(principal));
            return new ResponseEntity<>(fileInfo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not Upload.", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @RequestMapping(value = "/{documentId}/removeClinic/{clinicId}", method = RequestMethod.POST)
    public ResponseEntity removeClinicFromDocument(@PathVariable UUID documentId, @PathVariable UUID clinicId)
    {
        try {
            com.ectosense.nightowl.data.entity.FileInfo fileInfo =
                    medicalRecordService.removeClinicFromDocument(documentId, clinicId);
            return new ResponseEntity<>(fileInfo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not Upload.", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = "{patientId}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN') " +
            "or hasRole('ROLE_DOCTOR') or hasRole('ROLE_CLINIC') or hasRole('ROLE_PATIENT')")
    public ResponseEntity getDocumentsByPatient(@PathVariable UUID patientId)
    {
        List<com.ectosense.nightowl.data.entity.FileInfo> files = medicalRecordService.getDocumentsByPatient(patientId);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @RequestMapping(value = "{clinicId}/patient/{patientId}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN') " +
            "or hasRole('ROLE_DOCTOR') or hasRole('ROLE_CLINIC') or hasRole('ROLE_PATIENT')")
    public ResponseEntity getDocumentsByPatientAndClinic(@PathVariable UUID clinicId, @PathVariable UUID patientId)
    {
        List<com.ectosense.nightowl.data.entity.FileInfo> files =
                medicalRecordService.getDocumentsByClinicAndPatient(clinicId, patientId);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @RequestMapping(value = "/{documentId}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN') " +
            "or hasRole('ROLE_DOCTOR') or hasRole('ROLE_CLINIC') or hasRole('ROLE_PATIENT')")
    public ResponseEntity getDocument(@PathVariable UUID documentId, Principal principal)
    {
        Resource file = medicalRecordService.getDocument(documentId, getUser(principal));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    /*
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file)
    {
        fileStorageService.init();
        try {
            FileInfo fileInfo =  fileStorageService.save(file);
            return new ResponseEntity<>(fileInfo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not Upload.", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public ResponseEntity getListFiles()
    {
        List<FileInfo> fileInfos = fileStorageService.loadAll()
                .map(path -> {
                                String filename = path.getFileName().toString();
                                String url = MvcUriComponentsBuilder.fromMethodName(
                                        MedicalRecordController.class, "getFile",
                                        path.getFileName().toString())
                                        .build().toString();
                    return new FileInfo(filename, url);
                })
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @RequestMapping(value = "/files/{filename:.+}", method = RequestMethod.GET)
    public ResponseEntity getFile(@PathVariable String filename)
    {
        Resource file = fileStorageService.load(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }*/
}
