package com.ectosense.nightowl.service.impl;

import com.ectosense.nightowl.data.entity.Patient;
import com.ectosense.nightowl.data.entity.User;
import com.ectosense.nightowl.data.repository.PatientRepository;
import com.ectosense.nightowl.exception.ResourceExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PatientServiceImpl
{
    @Autowired
    private PatientRepository patientRepository;

    public Patient addPatient(Patient patient, User user)
    {
        if (patientRepository.findAllByAccountDetails(user) != null)
        {
            throw new ResourceExistException("You are already a registered Patient.");
        }
        patient.setAccountDetails(user);
        patient.setCreatedAt(new Date());
        return patientRepository.save(patient);
    }

    public Patient getPatientById(UUID patientId)
    {
        return patientRepository.getOne(patientId);
    }

    public Patient getPatientByUser(User user)
    {
        return patientRepository.findAllByAccountDetails(user);
    }
    public List<Patient> getAllPatient()
    {
        return patientRepository.findAll();
    }
}
