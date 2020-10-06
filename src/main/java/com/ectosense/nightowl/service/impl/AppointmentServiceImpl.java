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
}
