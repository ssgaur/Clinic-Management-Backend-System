package com.ectosense.nightowl.service.impl;

import com.ectosense.nightowl.data.entity.Appointment;
import com.ectosense.nightowl.data.entity.Clinic;
import com.ectosense.nightowl.data.entity.FileInfo;
import com.ectosense.nightowl.data.entity.Patient;
import com.ectosense.nightowl.data.repository.FileInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FileInfoServiceImpl
{
    @Autowired
    private FileInfoRepository fileInfoRepository;

    public FileInfo saveFile(FileInfo fileInfo)
    {
        return fileInfoRepository.save(fileInfo);
    }

    public List<FileInfo> getFilesByPatient(Patient patient)
    {
        return fileInfoRepository.findAllByPatient(patient);
    }

    public FileInfo getDocumentById(UUID documentId)
    {
        return fileInfoRepository.getOne(documentId);
    }

    public List<FileInfo> getFilesByClinics(List<Clinic> clinics)
    {
        return fileInfoRepository.findAllByClinics(clinics);
    }

    public List<FileInfo> getFilesByClinicsAndPatient(Patient patient, List<Clinic> clinics)
    {
        return fileInfoRepository.findAllByClinicsAndPatient(clinics, patient);
    }
}
