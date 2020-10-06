package com.ectosense.nightowl.service.impl;

import com.ectosense.nightowl.data.entity.Appointment;
import com.ectosense.nightowl.data.entity.FileInfo;
import com.ectosense.nightowl.data.repository.FileInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileInfoServiceImpl
{
    @Autowired
    private FileInfoRepository fileInfoRepository;

    public FileInfo saveFile(FileInfo fileInfo)
    {
        return fileInfoRepository.save(fileInfo);
    }

    public List<FileInfo> getFilesByAppointment(Appointment appointment)
    {
        return fileInfoRepository.findAllByAppointment(appointment);
    }
}
