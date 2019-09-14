package com.gsp.ns.gradskiPrevoz.fileStorage;

import com.gsp.ns.gradskiPrevoz.user.User;
import com.gsp.ns.gradskiPrevoz.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(value = "/api")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileStorageService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/statusRequest/uploadImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, Authentication auth) {
        User user = userService.getLoggedUser();
        String fileName = fileStorageService.storeFile(file,"images", user.getIdUser()+".jpg");
        return new ResponseEntity<String>(fileName,HttpStatus.OK);
    }

    @PostMapping(value = "/uploadImage/rekvizitt")
    public ResponseEntity<?> uploadImagee(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file,"rekviziti","333");

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/downloadFile/{folder}/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") String fileName, HttpServletRequest request,@PathVariable("folder") String folder) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(folder,fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    @GetMapping(value = "/downloadFile/{folder}/{id}/{broj}")
    public ResponseEntity<Resource> reloadFile(@PathVariable("id") String fileName,
                                               HttpServletRequest request,
                                               @PathVariable("folder") String folder,
                                               @PathVariable("id") String broj) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(folder,fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
