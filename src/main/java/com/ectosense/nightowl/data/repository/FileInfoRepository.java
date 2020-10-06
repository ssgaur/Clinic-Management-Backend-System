package com.ectosense.nightowl.data.repository;

import com.ectosense.nightowl.data.entity.Clinic;
import com.ectosense.nightowl.data.entity.FileInfo;
import com.ectosense.nightowl.data.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FileInfoRepository extends JpaRepository<FileInfo, UUID>
{
    List<FileInfo> findAllByPatient(Patient patient);
    List<FileInfo> findAllByClinics(List<Clinic> clinics);
    List<FileInfo> findAllByClinicsAndPatient(List<Clinic> clinics, Patient patient);
}
