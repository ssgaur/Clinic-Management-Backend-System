package com.ectosense.nightowl.service.impl;

import com.ectosense.nightowl.controller.AppointmentController;
import com.ectosense.nightowl.controller.MedicalRecordController;
import com.ectosense.nightowl.data.entity.Appointment;
import com.ectosense.nightowl.data.entity.Assistant;
import com.ectosense.nightowl.data.entity.Clinic;
import com.ectosense.nightowl.data.entity.Doctor;
import com.ectosense.nightowl.data.entity.Patient;
import com.ectosense.nightowl.data.entity.User;
import com.ectosense.nightowl.data.entity.FileInfo;
import com.ectosense.nightowl.data.repository.AppointmentRepository;
import com.ectosense.nightowl.exception.ResourceExistException;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentServiceImpl
{
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ClinicServiceImpl clinicService;

    @Autowired
    private DoctorServiceImpl doctorService;

    @Autowired
    private PatientServiceImpl  patientService;

    @Autowired
    private AssistantServiceImpl assistantService;

    @Autowired
    private FileInfoServiceImpl fileInfoService;

    private final Path root = Paths.get("uploads");

    public Appointment createAppointment(UUID clinicId, UUID doctorId, UUID patientId, User user,
                                         Appointment appointment)
    {
        if (clinicId == null || doctorId == null || patientId == null)
        {
            throw new IllegalArgumentException("Invalid Doctor or Clinic or Patient");
        }
        Doctor  doctor  = doctorService.getDoctorById(doctorId);
        Clinic  clinic  = clinicService.getClinicById(clinicId);
        Patient patient = patientService.getPatientById(patientId);
        if (doctor == null || clinic == null || patient == null)
        {
            throw new ResourceNotFoundException("Doctor or Clinic or Patient not found !");
        }
        if (!clinic.getDoctorList().contains(doctor))
        {
            throw new ResourceNotFoundException("Doctor does not belong to this Clinic !");
        }
        Assistant assistant = assistantService.getAssistantByUser(user);
        if (assistant == null)
        {
            throw new ResourceNotFoundException("Assistant not found !");
        }
        if (!clinic.getAssistantList().contains(assistant))
        {
            throw new ResourceNotFoundException("Assistant does not belong to this Clinic !");
        }

        List<Appointment> appointmentList =  appointmentRepository.getAppointmentInTimeInterval(
                appointment.getStartTime(), appointment.getEndTime(), clinic, doctor);
        if (appointmentList != null && !appointmentList.isEmpty())
        {
            throw new ResourceExistException("This time slot is already booked for this Doctor and Clinic");
        }

        appointment.setAssistant(assistant);
        appointment.setClinic(clinic);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setCreatedAt(new Date());

        return appointmentRepository.save(appointment);
    }

    public Appointment getAppointmentById(UUID appointmentId)
    {
        return appointmentRepository.getOne(appointmentId);
    }

    Path createDirectoryIfNotExists(String folder)
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

    public FileInfo saveDocumemt(UUID appointmentId, MultipartFile file)
    {
        Appointment appointment = appointmentRepository.getOne(appointmentId);
        if (appointment == null)
        {
            throw new ResourceNotFoundException("No Appointment found to Upload document.");
        }
        Path uploadDir = createDirectoryIfNotExists("/"+appointmentId.toString());
        try
        {
            Files.copy(file.getInputStream(), uploadDir.resolve(file.getOriginalFilename()));
            String url = MvcUriComponentsBuilder.fromMethodName(
                    AppointmentController.class, "getDocument",
                    appointmentId,
                    file.getOriginalFilename())
                    .build().toString();
            FileInfo fileInfo = new FileInfo();
            fileInfo.setAppointment(appointment);
            fileInfo.setFileName(file.getOriginalFilename());
            fileInfo.setFileUrl(url);
            return fileInfoService.saveFile(fileInfo);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public List<FileInfo> getDocumentsByAppointment(UUID appointmentId)
    {
        Appointment appointment = appointmentRepository.getOne(appointmentId);
        if (appointment == null)
        {
            throw new ResourceNotFoundException("No Appointment found to Upload document.");
        }
        return fileInfoService.getFilesByAppointment(appointment);
    }

    public Resource getDocument(UUID appointmentId, String filename) {
        try
        {
            Path root  = Paths.get("uploads/"+appointmentId);
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
