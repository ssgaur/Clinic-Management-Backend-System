package com.ectosense.nightowl.service.impl;

import com.ectosense.nightowl.controller.MedicalRecordController;
import com.ectosense.nightowl.data.model.FileInfo;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Service
public class FilesStorageServiceImpl
{
    private final Path root = Paths.get("uploads");

    public void init() {
        try
        {
            Files.createDirectory(root);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public FileInfo save(MultipartFile file) {
        try
        {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
            String url = MvcUriComponentsBuilder.fromMethodName(
                    MedicalRecordController.class, "getFile",
                    file.getOriginalFilename())
                    .build().toString();
            return new FileInfo(file.getOriginalFilename(), url);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public Resource load(String filename) {
        try
        {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable())
            {
                return resource;
            } else
            {
                throw new RuntimeException("Could not read the file!");
            }
        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    public Stream<Path> loadAll() {
        try
        {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Could not load the files!");
        }
    }

}
