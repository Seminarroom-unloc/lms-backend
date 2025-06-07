package com.seminarroom.edu.config;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
public class CertificateService {

    public byte[] generateCertificatePDF(String name) throws IOException {
        // Load the HTML template
        InputStream inputStream = getClass().getResourceAsStream("/templates/certificate.html");
        String htmlTemplate = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        String filledHtml = htmlTemplate.replace("{{name}}", name);

        // Generate PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withHtmlContent(filledHtml, null);
        builder.toStream(outputStream);
        builder.run();

        return outputStream.toByteArray();
    }
}
