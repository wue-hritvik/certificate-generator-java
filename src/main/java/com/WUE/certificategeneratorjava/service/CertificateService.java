package com.WUE.certificategeneratorjava.service;

import com.WUE.certificategeneratorjava.dto.TextWithCoordinatesDto;
import com.WUE.certificategeneratorjava.model.Certificate;
import com.WUE.certificategeneratorjava.repository.GenerateCertificateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class CertificateService {

    @Autowired
    private GenerateCertificateRepository generateCertificateRepository;

    public ResponseEntity<?> generateCertificates(MultipartFile imageFile, List<List<TextWithCoordinatesDto>> data) {
        try {
            log.info("Reading image file");

            BufferedImage image = ImageIO.read(imageFile.getInputStream());

            for (List<TextWithCoordinatesDto> text : data) {

                BufferedImage jpgImage=image;
                if(!Objects.requireNonNull(imageFile.getOriginalFilename()).contains(".jpg")){
                    jpgImage = convertToJPEG(image);
                }

                String id = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
                addTextsToImage(jpgImage, text, id);
                byte[] imageData = convertImageToByteArray(jpgImage);
                Certificate certificate = new Certificate();
                certificate.setImageData(imageData);
                certificate.setId(id);
                generateCertificateRepository.save(certificate);
            }

            return new ResponseEntity("All the works are done", HttpStatus.OK);
        } catch (IOException e) {
            log.error("Error while generating certificate: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error while generating certificate");
        }
    }

    private BufferedImage convertToJPEG(BufferedImage image) {
        BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        convertedImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
        return convertedImage;
    }

    private void addTextsToImage(BufferedImage image, List<TextWithCoordinatesDto> data, String id) {
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 40));

        for (TextWithCoordinatesDto text : data) {
            String texts = text.getText();
            int x = text.getX();
            int y = text.getY();
            g2d.drawString(texts, x, y);
        }
        g2d.drawString("Certificate ID: "+id, 180, 1000);
        g2d.dispose();
        log.info("Texts added to image");
    }

    private byte[] convertImageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        return outputStream.toByteArray();
    }

    public ResponseEntity<Resource> downloadCertificate(String id) {
        byte[] image = generateCertificateRepository.findById(id).get().getImageData();
        ByteArrayResource resource = new ByteArrayResource(image);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"certificate.jpg\"")
                .contentLength(resource.contentLength())
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    public ResponseEntity<Resource> previewCertificate(String id) {
        byte[] image = generateCertificateRepository.findById(id).get().getImageData();
        ByteArrayResource resource = new ByteArrayResource(image);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}
