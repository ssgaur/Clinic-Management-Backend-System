package com.ectosense.nightowl.service.impl;

import com.ectosense.nightowl.data.entity.Assistant;
import com.ectosense.nightowl.data.entity.Clinic;
import com.ectosense.nightowl.data.entity.User;
import com.ectosense.nightowl.data.repository.ClinicRepository;
import com.ectosense.nightowl.exception.ResourceExistException;
import com.ectosense.nightowl.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class ClinicServiceImpl
{
    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private AssistantServiceImpl assistantService;

    public Clinic addClinic(Clinic clinic, User user)
    {
        if (clinicRepository.findByAccountDetails(user) != null)
        {
            throw new ResourceExistException("Clinic with same Email already Registered.");
        }
        clinic.setAccountDetails(user);
        clinic.setCreatedAt(new Date());
        return clinicRepository.save(clinic);
    }

    public List<Clinic> getAllClinic()
    {
        return clinicRepository.findAll();
    }

    public List<Assistant> getAllAssistants(UUID clinicId)
    {
        Clinic clinic = clinicRepository.getOne(clinicId);
        if (clinic == null)
        {
            throw new ResourceNotFoundException("Clinic not found.");
        }
        return assistantService.getAssistantsByClinic(clinic);
    }

    public Clinic getClinicById(UUID clinicId)
    {
        return clinicRepository.getOne(clinicId);
    }

    public Clinic saveClinic(Clinic clinic)
    {
        return clinicRepository.save(clinic);
    }
}
