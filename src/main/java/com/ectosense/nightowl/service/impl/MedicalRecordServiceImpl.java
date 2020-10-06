package com.ectosense.nightowl.service.impl;

import com.ectosense.nightowl.controller.MedicalRecordController;
import com.ectosense.nightowl.data.entity.Clinic;
import com.ectosense.nightowl.data.entity.FileInfo;
import com.ectosense.nightowl.data.entity.Patient;
import com.ectosense.nightowl.data.entity.User;
import com.ectosense.nightowl.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class MedicalRecordServiceImpl
{
    @Autowired
    private PatientServiceImpl patientService;

    @Autowired
    private ClinicServiceImpl clinicService;

    @Autowired
    private FileInfoServiceImpl fileInfoService;

    private final Path root = Paths.get("uploads");

    private Path createDirectoryIfNotExists(String folder)
    {
        try
        {
            Path root  = Paths.get("uploads"+folder);
            Files.createDirectories(root);
            return root;
        }
        catch (IOException e)
        {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public FileInfo saveDocument(User user, MultipartFile file)
    {
        Patient patient = patientService.getPatientByUser(user);
        if (patient == null)
        {
            throw new ResourceNotFoundException("Patient not found");
        }
        Path uploadDir = createDirectoryIfNotExists("/"+patient.getId().toString());
        try
        {
            Files.copy(file.getInputStream(), uploadDir.resolve(file.getOriginalFilename()));
            String url = MvcUriComponentsBuilder.fromMethodName(
                    MedicalRecordController.class, "getDocument",
                    patient.getId(),
                    file.getOriginalFilename())
                    .build().toString();
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(file.getOriginalFilename());
            fileInfo.setFileUrl(url);
            return fileInfoService.saveFile(fileInfo);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public FileInfo removeClinicFromDocument(UUID documentId, UUID clinicId)
    {
        FileInfo fileInfo = fileInfoService.getDocumentById(documentId);
        if (fileInfo == null)
        {
            throw new ResourceNotFoundException("File not found.");
        }
        Clinic clinic = clinicService.getClinicById(clinicId);
        if (clinic == null)
        {
            throw  new ResourceNotFoundException("Clinic Not found");
        }
        if (fileInfo.getClinics().contains(clinic))
        {
            fileInfo.getClinics().remove(clinic);
        }
        return fileInfoService.saveFile(fileInfo);
    }

    public FileInfo saveDocumentForClinic(UUID clinicId, MultipartFile file, User user)
    {
        Clinic clinic = clinicService.getClinicById(clinicId);
        if (clinic == null)
        {
            throw  new ResourceNotFoundException("Clinic Not found");
        }
        FileInfo fileInfo = saveDocument(user, file);
        if (!fileInfo.getClinics().contains(clinic))
        {
            fileInfo.getClinics().add(clinic);
        }
        return fileInfoService.saveFile(fileInfo);
    }

    public List<FileInfo> getDocumentsByClinicAndPatient(UUID clinicId, UUID patientId)
    {
        Clinic clinic   =  clinicService.getClinicById(clinicId);
        Patient patient =  patientService.getPatientById(patientId);
        if (clinic == null || patient == null)
        {
            throw new ResourceNotFoundException("No documents shared with this clinic");
        }
        return fileInfoService.getFilesByClinicsAndPatient(patient, Arrays.asList(clinic));
    }

    public List<FileInfo> getDocumentsByPatient(UUID patientId)
    {
        Patient patient =  patientService.getPatientById(patientId);
        if (patient == null)
        {
            throw new ResourceNotFoundException("No Patient found.");
        }
        return fileInfoService.getFilesByPatient(patient);
    }

    public Resource getDocument(UUID patientId, String filename) {
        try
        {
            Path root  = Paths.get("uploads/"+patientId);
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable())
            {
                return resource;
            } else
            {
                throw new RuntimeException("Could not read the file!");
            }
        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
