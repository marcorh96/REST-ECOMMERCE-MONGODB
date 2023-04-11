package com.marcorh96.springboot.rest.ecommerce.app.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.marcorh96.springboot.rest.ecommerce.app.models.document.Product;
import com.marcorh96.springboot.rest.ecommerce.app.models.services.ICategoryService;
import com.marcorh96.springboot.rest.ecommerce.app.models.services.IManufacturerService;
import com.marcorh96.springboot.rest.ecommerce.app.models.services.IProductService;
import com.marcorh96.springboot.rest.ecommerce.app.models.services.file.IUploadFileService;
import com.mongodb.DuplicateKeyException;

import java.io.IOException;
import java.net.MalformedURLException;

@CrossOrigin(origins = { "http://127.0.0.1:5173/", "*" })
@RestController
@RequestMapping("/api")
public class ProductRestController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IManufacturerService manufacturerService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    @Qualifier("upload-products")
    private IUploadFileService uploadFileService;

    @GetMapping("/products")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public List<Product> showProducts() {
        return productService.findAll();
    }

    @GetMapping("/products/page/{page}")
    public Page<Product> showProducts(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 8);
        return productService.findAll(pageable);
    }

    @GetMapping("/products/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public Product showOrder(@PathVariable String id) {
        return productService.findById(id);
    }

    @PostMapping("/products")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> create(@RequestBody Product product) {

        Map<String, Object> response = new HashMap<>();
        try {
            product.setCreatedAt(new Date());
            categoryService.save(product.getCategory());
            manufacturerService.save(product.getManufacturer());
            productService.save(product);
        } catch (DuplicateKeyException e) {
            response.put("mensaje", "Error: Email already exists!");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            response.put("message", "Data Base Exception!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Product has been created successfully");
        response.put("product", product);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    @DeleteMapping("/products/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Product product = productService.findById(id);
            String photoPastName = product.getPhoto();
            uploadFileService.delete(photoPastName);
            productService.delete(id);
        } catch (DataAccessException e) {
            response.put("message", "Data Base Exception!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Product has been deleted successfully!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }

    @PutMapping("/products/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Product product) {

        Map<String, Object> response = new HashMap<>();
        Product actualProduct = null;
        try {

            actualProduct = productService.findById(id);
            actualProduct.setName(product.getName());
            actualProduct.setDescription(product.getDescription());
            actualProduct.setCategory(product.getCategory());
            actualProduct.setFeatures(actualProduct.getFeatures());
            actualProduct.setPrice(product.getPrice());
            actualProduct.setStock(product.getStock());
            actualProduct.setColor(product.getColor());
            actualProduct.setManufacturer(product.getManufacturer());
            actualProduct.setUpdatedAt(new Date());

            categoryService.save(actualProduct.getCategory());
            manufacturerService.save(actualProduct.getManufacturer());
            productService.save(actualProduct);
        } catch (DataAccessException e) {
            response.put("message", "Data Base Exception!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Product has been updated successfully!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }

    @PostMapping("/products/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) {
        Map<String, Object> response = new HashMap<>();
        Product product = productService.findById(id);
        if (!file.isEmpty()) {
            String nombreArchivo = null;
            try {
                nombreArchivo = uploadFileService.copy(file);
            } catch (IOException e) {
                response.put("message", "Error uploading image" + nombreArchivo);
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String photoPastName = product.getPhoto();

            uploadFileService.delete(photoPastName);
            product.setPhoto(nombreArchivo);
            productService.save(product);
            response.put("product", product);
            response.put("message", "Image has been uploaded successfully: " + nombreArchivo);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/products/upload/img/{photoName:.+}")
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
