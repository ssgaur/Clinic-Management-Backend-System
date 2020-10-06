package com.ectosense.nightowl.data.repository;

import com.ectosense.nightowl.data.entity.Doctor;
import com.ectosense.nightowl.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID>
{
    Doctor findByAccountDetails(User accountDetails);
}
