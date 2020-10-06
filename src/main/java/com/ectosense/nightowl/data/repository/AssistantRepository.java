package com.ectosense.nightowl.data.repository;

import com.ectosense.nightowl.data.entity.Assistant;
import com.ectosense.nightowl.data.entity.Clinic;
import com.ectosense.nightowl.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssistantRepository extends JpaRepository<Assistant, UUID>
{
    Assistant findByAccountDetails(User accountDetails);

    Assistant findAllByAccountDetailsAndClinic(User accountDetails, Clinic clinic);

    List<Assistant> findAllByClinic(Clinic clinic);
}
