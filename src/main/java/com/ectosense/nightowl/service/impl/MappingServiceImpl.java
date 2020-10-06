package com.ectosense.nightowl.service.impl;

import com.ectosense.nightowl.data.entity.Assistant;
import com.ectosense.nightowl.data.entity.Clinic;
import com.ectosense.nightowl.data.entity.Doctor;
import com.ectosense.nightowl.exception.ResourceExistException;
import com.ectosense.nightowl.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MappingServiceImpl
{
    @Autowired
    private DoctorServiceImpl doctorService;

    @Autowired
    private ClinicServiceImpl clinicService;

    @Autowired
    private AssistantServiceImpl assistantService;

    public Clinic attachDoctorToClinic(UUID clinicId, UUID doctorId)
    {
        if (clinicId != null && doctorId != null)
        {
            Clinic clinic = clinicService.getClinicById(clinicId);
            Doctor doctor = doctorService.getDoctorById(doctorId);
            if (clinic == null || doctor == null)
            {
                throw new ResourceNotFoundException("Doctor or Clinic Not Found!");
            }
            if (!clinic.getDoctorList().contains(doctor))
            {
                clinic.getDoctorList().add(doctor);
            }
            if (!doctor.getClinicList().contains(clinic))
            {
                doctor.getClinicList().add(clinic);
            }
            doctorService.saveDoctor(doctor);
            return clinicService.saveClinic(clinic);

        }
        throw new IllegalArgumentException("Invalid Doctor or Clinic Id provided!");
    }

    public Doctor attachClinicToDoctor(UUID clinicId, UUID doctorId)
    {
        if (clinicId != null && doctorId != null)
        {
            Clinic clinic = clinicService.getClinicById(clinicId);
            Doctor doctor = doctorService.getDoctorById(doctorId);
            if (clinic == null || doctor == null)
            {
                throw new ResourceNotFoundException("Doctor or Clinic Not Found!");
            }
            if (!doctor.getClinicList().contains(clinic))
            {
                doctor.getClinicList().add(clinic);
            }
            if (!clinic.getDoctorList().contains(doctor))
            {
                clinic.getDoctorList().add(doctor);
            }
            clinicService.saveClinic(clinic);
            return doctorService.saveDoctor(doctor);
        }
        throw new IllegalArgumentException("Invalid Doctor or Clinic Id provided!");
    }

    public Clinic attachAssistantToClinic(UUID clinicId, UUID assistantId)
    {
        if (clinicId != null && assistantId != null)
        {
            Clinic    clinic    = clinicService.getClinicById(clinicId);
            Assistant assistant = assistantService.getAssistantById(assistantId);
            if (clinic == null || assistant == null)
            {
                throw new ResourceNotFoundException("Assistant or Clinic Not Found!");
            }
            if (assistant.getClinic() != null)
            {
                throw new ResourceExistException("Assistant already working at another clinic.");
            }
            if (!clinic.getAssistantList().contains(assistant))
            {
                clinic.getAssistantList().add(assistant);
            }
            assistant.setClinic(clinic);
            assistantService.saveAssistant(assistant);
            return clinicService.saveClinic(clinic);
        }
        throw new IllegalArgumentException("Invalid Assistant or Clinic Id provided!");
    }
}
