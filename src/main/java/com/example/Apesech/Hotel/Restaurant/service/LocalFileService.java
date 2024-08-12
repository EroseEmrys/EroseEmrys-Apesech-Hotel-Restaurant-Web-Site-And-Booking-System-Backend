package com.example.Apesech.Hotel.Restaurant.service;

import com.example.Apesech.Hotel.Restaurant.exception.OurException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LocalFileService {

    @Value("D:/New Project/apesech-hotel/public/assets/images/Uploades")
    private String uploadDir;

    public String saveImageToLocal(MultipartFile photo) {
        try {
            String filename = photo.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, filename);

            // Ensure the upload directory exists
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Save the file locally
            try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                fos.write(photo.getBytes());
            }

            return filePath.toUri().toString();

        } catch (IOException e) {
            e.printStackTrace();
            throw new OurException("Unable to upload image to local directory: " + e.getMessage());
        }
    }
}
