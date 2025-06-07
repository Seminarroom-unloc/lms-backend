package com.seminarroom.edu.controller;

import com.seminarroom.edu.config.CertificateService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/certificate")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadPdfCertificate(HttpServletRequest request) {
        try {
            String username = extractUsernameFromCookie(request);

            // TODO: replace with real logic
            if (!hasCompletedAllModules(username)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            byte[] pdf = certificateService.generateCertificatePDF(username);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=certificate.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new ByteArrayResource(pdf));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String extractUsernameFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if ("username".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return "Anonymous";
    }

    private boolean hasCompletedAllModules(String username) {
        // Replace with DB logic
        return true;
    }
}
