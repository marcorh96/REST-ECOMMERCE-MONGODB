package com.marcorh96.springboot.rest.ecommerce.app.models.services.file;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import lombok.extern.log4j.Log4j2;

@Service("upload-products")
@Log4j2
public class UploadFileServiceProductImpl implements IUploadFileService {

    private final static String UPLOAD_DIR = "springboot-rest-ecommerce/upload/products";

    @Override
    public Resource load(String photoName) throws MalformedURLException {
        Path filePath = this.getPath(photoName);
        log.info(filePath.toString());
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() && !resource.isReadable()) {

            filePath = Paths.get("src/main/resources/static/images")
                    .resolve("no-photo.jpg")
                    .toAbsolutePath();
            resource = new UrlResource(filePath.toUri());
            log.error("Error: cant load image " + photoName);
        }
        return resource;
    }

    @Override
    public String copy(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replace(" ", "");
        Path filePath = getPath(fileName);
        log.info(filePath.toString());
        Files.copy(file.getInputStream(), filePath);
        return fileName;
    }

    @Override
    public boolean delete(String photoName) {
        if (photoName != null && photoName.length() > 0) {
            Path pathLastPhoto = Paths.get(UPLOAD_DIR).resolve(photoName)
                    .toAbsolutePath();
            File fileLastPhoto = pathLastPhoto.toFile();
            if (fileLastPhoto.exists() && fileLastPhoto.canRead()) {
                fileLastPhoto.delete();
                return true;
            }
        }
        return false;
    }

    @Override
    public Path getPath(String photoName) {
        return Paths.get(UPLOAD_DIR).resolve(photoName).toAbsolutePath();
    }

}
