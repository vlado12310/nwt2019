package com.gsp.ns.gradskiPrevoz.fileStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    private final Path fileStorageLocation;

    @Autowired
    public FileService(FileProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
                    ex);
        }
    }

    public String storeFile(MultipartFile file, String folderS, String fileName) {
        // Normalize file name

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            File folder = Paths.get(this.fileStorageLocation.toString(), folderS).toFile();
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = Paths.get(folder.toString(), fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            System.out.println(targetLocation.toString());

            return targetLocation.toString();
        } catch (Exception ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public void deleteFile(String folderS, String fileName) {
        // Normalize file name

        // Check if the file's name contains invalid characters
        if (fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }

        File folder = Paths.get(this.fileStorageLocation.toString(), folderS).toFile();
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Copy file to the target location (Replacing existing file with the same name)
        Path targetLocation = Paths.get(folder.toString(), fileName);
        File file = new File(targetLocation.toString());
        if (file.delete()) {
            System.out.println("Izbrisan fajl: "+targetLocation.toString());
        } else {
            System.out.println("Nije pronadjen fajl: "+targetLocation.toString());
        }
    }

    public Resource loadFileAsResource(String folderS, String fileName) {
        try {

            Path filePath = Paths.get(this.fileStorageLocation.toString(), folderS, fileName);// nomalize mozda treba
            // dodati ako bude
            // frkice

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public static class FileStorageException extends RuntimeException {
        public FileStorageException(String message) {
            super(message);
        }

        public FileStorageException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class MyFileNotFoundException extends RuntimeException {
        public MyFileNotFoundException(String message) {
            super(message);
        }

        public MyFileNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
