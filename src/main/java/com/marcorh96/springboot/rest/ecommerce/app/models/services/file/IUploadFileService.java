package com.marcorh96.springboot.rest.ecommerce.app.models.services.file;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService{
    
    public Resource load(String photoName) throws MalformedURLException;

    public String copy(MultipartFile file) throws IOException;
    
    public boolean delete(String photoName);

    public Path getPath(String photoName);
}
