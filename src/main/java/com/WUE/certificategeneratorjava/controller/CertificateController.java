package com.WUE.certificategeneratorjava.controller;


import com.WUE.certificategeneratorjava.dto.TextWithCoordinatesDto;

import com.WUE.certificategeneratorjava.service.CertificateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api")
@Slf4j
public class CertificateController {


    @Autowired
    private CertificateService certificateService;

    @PostMapping("/generateCertificate")
    public ResponseEntity<?> generateCertificate(
            @RequestPart("image") MultipartFile imageFile,
            @RequestPart("data") List<List<TextWithCoordinatesDto>> data) {

        return certificateService.generateCertificates(imageFile, data);

    }

    @GetMapping("/downloadCertificate/{id}")
    public ResponseEntity<Resource> downloadCertificate(@PathVariable String id) throws IOException {

        return certificateService.downloadCertificate(id);

    }

    @GetMapping("/previewCertificate/{id}")

    public ResponseEntity<Resource> previewCertificate(@PathVariable String id) {
        return certificateService.previewCertificate(id);

    }
}
