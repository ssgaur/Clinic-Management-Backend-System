package com.ectosense.nightowl.data.repository;

import com.ectosense.nightowl.data.entity.Appointment;
import com.ectosense.nightowl.data.entity.Assistant;
import com.ectosense.nightowl.data.entity.Clinic;
import com.ectosense.nightowl.data.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID>
{
    List<Appointment> findAllByDoctor(Doctor doctor);
    List<Appointment> findAllByAssistant(Assistant assistant);
    List<Appointment> findAllByClinic(Clinic clinic);

    @Query("SELECT a from Appointment a WHERE a.clinic = :clinic AND a.doctor = :doctor AND  " +
            "( ( :startTime BETWEEN a.startTime AND a.endTime ) OR  ( :endTime BETWEEN a.startTime AND a.endTime) )")
    List<Appointment> getAppointmentInTimeInterval(@Param("startTime") Date startTime,
                                                   @Param("endTime") Date endTime,
                                                   @Param("clinic") Clinic clinic,
                                                   @Param("doctor") Doctor doctor);
}
