package com.ectosense.nightowl.service.impl;

import com.ectosense.nightowl.data.entity.Doctor;
import com.ectosense.nightowl.data.entity.User;
import com.ectosense.nightowl.data.repository.DoctorRepository;
import com.ectosense.nightowl.exception.ResourceExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DoctorServiceImpl
{
    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor addDoctor(Doctor doctor, User user)
    {
        if (doctorRepository.findByAccountDetails(user) != null)
        {
            throw new ResourceExistException("Doctor with same Email already Registered.");
        }
        doctor.setAccountDetails(user);
        doctor.setCreatedAt(new Date());
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors()
    {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(UUID doctorId)
    {
        return doctorRepository.getOne(doctorId);
    }

    public Doctor saveDoctor(Doctor doctor)
    {
        return doctorRepository.save(doctor);
    }
}
