package com.marcorh96.springboot.rest.ecommerce.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.marcorh96.springboot.rest.ecommerce.app.models.auth.UserLoginDTO;
import com.marcorh96.springboot.rest.ecommerce.app.models.auth.UserResponseDTO;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.User;
import com.marcorh96.springboot.rest.ecommerce.app.models.services.IUserService;
import com.marcorh96.springboot.rest.ecommerce.app.models.services.file.IUploadFileService;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private IUserService userService;

    @Autowired
    @Qualifier("upload-users")
    private IUploadFileService uploadFileService;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> showUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/page/{page}")
    public Page<User> showUsers(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 2);
        return userService.findAll(pageable);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public User showUser(@PathVariable String id) {
        return userService.findById(id);
    }

    @PostMapping("/users/signin")
    public ResponseEntity<?> createUser(@RequestBody @Validated User user) {

        Map<String, Object> response = new HashMap<>();
        try {
            user.setCreatedAt(new Date());
            userService.save(user);
        } catch (DataAccessException e) {
            response.put("mensaje", "Data Base Exception!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "User has been created successfully");
        response.put("user", user);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = userService.findById(id);
            String photoPastName = user.getPhoto();
            uploadFileService.delete(photoPastName);
            userService.deleteOrdersByUserId(id);
            userService.delete(id);
        } catch (DataAccessException e) {
            response.put("message", "Data Base Exception!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "User has been deleted successfully!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasAuthority({'ROLE_USER', 'ROLE_ADMIN'})")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody User user) {

        Map<String, Object> response = new HashMap<>();
        try {
            User actualUser = userService.findById(id);
            actualUser.setPerson(user.getPerson());
            actualUser.setEmail(user.getEmail());
            actualUser.setPassword(user.getPassword());
            actualUser.setAddress(user.getAddress());
            actualUser.setRole(user.getRole());
            userService.save(actualUser);

        } catch (DataAccessException e) {
            response.put("message", "Data Base Exception!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "User has been updated successfully!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO user) {
        Map<String, Object> response = new HashMap<>();
        UserResponseDTO userTokenResponse = userService.login(user);
        response.put("user", userTokenResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/users/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) {
        Map<String, Object> response = new HashMap<>();
        User user = userService.findById(id);
        if (!file.isEmpty()) {
            String nombreArchivo = null;
            try {
                nombreArchivo = uploadFileService.copy(file);
            } catch (IOException e) {
                response.put("message", "Error uploading image" + nombreArchivo);
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String photoPastName = user.getPhoto();

            uploadFileService.delete(photoPastName);
            user.setPhoto(nombreArchivo);
            userService.save(user);
            response.put("user", user);
            response.put("message", "Image has been uploaded successfully: " + nombreArchivo);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/users/upload/img/{photoName:.+}")
    public ResponseEntity<Resource> viewPhoto(@PathVariable String photoName) {

        Resource resource = null;
        try {
            resource = uploadFileService.load(photoName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }

}
