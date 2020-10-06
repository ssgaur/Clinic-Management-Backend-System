package com.ectosense.nightowl.data.repository;

import com.ectosense.nightowl.data.entity.Clinic;
import com.ectosense.nightowl.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClinicRepository extends JpaRepository<Clinic, UUID>
{
    Clinic findByAccountDetails(User accountDetails);
}
