package com.ectosense.nightowl.controller;


import com.ectosense.nightowl.data.model.FileInfo;
import com.ectosense.nightowl.service.impl.FilesStorageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@code MedicalRecordController} is the  Controller to handle all Medical Document related End points.
 * @author  Shailendra Singh
 */
@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/v1/documents")
public class MedicalRecordController
{
    @Autowired
    private FilesStorageServiceImpl fileStorageService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file)
    {
        fileStorageService.init();
        try {
            FileInfo fileInfo =  fileStorageService.save(file);
            return new ResponseEntity<>(fileInfo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not Upload.", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public ResponseEntity getListFiles()
    {
        List<FileInfo> fileInfos = fileStorageService.loadAll()
                .map(path -> {
                                String filename = path.getFileName().toString();
                                String url = MvcUriComponentsBuilder.fromMethodName(
                                        MedicalRecordController.class, "getFile",
                                        path.getFileName().toString())
                                        .build().toString();
                    return new FileInfo(filename, url);
                })
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @RequestMapping(value = "/files/{filename:.+}", method = RequestMethod.GET)
    public ResponseEntity getFile(@PathVariable String filename)
    {
        Resource file = fileStorageService.load(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
