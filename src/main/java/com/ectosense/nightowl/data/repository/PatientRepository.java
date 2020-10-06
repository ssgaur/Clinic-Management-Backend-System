package com.ectosense.nightowl.data.repository;

import com.ectosense.nightowl.data.entity.Patient;
import com.ectosense.nightowl.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID>
{
    Patient findAllByAccountDetails(User accountDetail);
}
