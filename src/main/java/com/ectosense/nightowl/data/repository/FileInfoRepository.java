package com.ectosense.nightowl.data.repository;

import com.ectosense.nightowl.data.entity.Appointment;
import com.ectosense.nightowl.data.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FileInfoRepository extends JpaRepository<FileInfo, UUID>
{
    List<FileInfo> findAllByAppointment(Appointment appointment);
}
