package com.ectosense.nightowl.service.impl;

import com.ectosense.nightowl.data.entity.Assistant;
import com.ectosense.nightowl.data.entity.Clinic;
import com.ectosense.nightowl.data.entity.User;
import com.ectosense.nightowl.data.repository.AssistantRepository;
import com.ectosense.nightowl.exception.ResourceExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AssistantServiceImpl
{
    @Autowired
    private AssistantRepository assistantRepository;

    public Assistant addAssistant(Assistant assistant, User user)
    {
        if (assistantRepository.findByAccountDetails(user) != null)
        {
            throw new ResourceExistException("Clinic with same Email already Registered.");
        }
        assistant.setAccountDetails(user);
        assistant.setCreatedAt(new Date());
        return assistantRepository.save(assistant);
    }

    public Assistant getAssistantByUser(User user)
    {
        return assistantRepository.findByAccountDetails(user);
    }

    public List<Assistant> getAllAssistant()
    {
        return assistantRepository.findAll();
    }

    public Assistant getAssistantById(UUID assistantId)
    {
        return assistantRepository.getOne(assistantId);
    }

    public List<Assistant> getAssistantsByClinic(Clinic clinic)
    {
        return assistantRepository.findAllByClinic(clinic);
    }

    public Assistant saveAssistant(Assistant assistant)
    {
        return assistantRepository.save(assistant);
    }
}
