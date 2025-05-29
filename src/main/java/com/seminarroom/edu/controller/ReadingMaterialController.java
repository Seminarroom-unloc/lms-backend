package com.seminarroom.edu.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.seminarroom.edu.model.ReadingMaterial;
import com.seminarroom.edu.repository.ReadingMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/modules/{moduleId}/reading-materials")
public class ReadingMaterialController {

    @Autowired
    private ReadingMaterialRepository repository;

    @Autowired
    private AmazonS3 amazonS3;

    private static final String BUCKET_NAME = "seminarroom-files"; // Replace with your S3 bucket

    @GetMapping
    public List<ReadingMaterial> getReadingMaterialsByModuleId(@PathVariable Long moduleId) {
        return repository.findByModuleId(moduleId);
    }

    @PostMapping
    public ReadingMaterial create(@RequestBody ReadingMaterial material) {
        return repository.save(material);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<?> downloadPdfFile(
            @PathVariable Long moduleId,
            @PathVariable Long id) {

        Optional<ReadingMaterial> materialOptional = repository.findById(id);

        if (materialOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reading material not found");
        }

        ReadingMaterial material = materialOptional.get();
        String s3Url = material.getS3Url();

        if (s3Url == null || s3Url.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("S3 URL not found");
        }

        try {
            // Example s3Url: s3://seminarroom-files/seminar files/Course_1/Module_1/html_tutorial.pdf
            // We parse the bucket and key from the s3Url string
            if (!s3Url.startsWith("s3://")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid S3 URI format");
            }

            String withoutScheme = s3Url.substring(5); // Remove "s3://"
            int firstSlashIndex = withoutScheme.indexOf('/');

            if (firstSlashIndex < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid S3 URI format");
            }

            String bucket = withoutScheme.substring(0, firstSlashIndex);
            String key = withoutScheme.substring(firstSlashIndex + 1);

            if (!bucket.equals(BUCKET_NAME)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bucket name mismatch");
            }

            // Now get object from S3
            S3Object s3Object = amazonS3.getObject(bucket, key);
            InputStream inputStream = s3Object.getObjectContent();

            byte[] fileBytes = inputStream.readAllBytes();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("attachment")
                    .filename(key.substring(key.lastIndexOf('/') + 1)) // use actual filename
                    .build());
            headers.setContentLength(fileBytes.length);

            return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error downloading file");
        }
    }

}

