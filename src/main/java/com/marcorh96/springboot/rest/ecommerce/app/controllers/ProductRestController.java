package com.marcorh96.springboot.rest.ecommerce.app.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcorh96.springboot.rest.ecommerce.app.models.document.Product;
import com.marcorh96.springboot.rest.ecommerce.app.models.services.IProductService;
import com.mongodb.DuplicateKeyException;

@RestController
@RequestMapping("/api")
public class ProductRestController {

    @Autowired
    private IProductService productService;

    @GetMapping("/products")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public List<Product> show() {
        return productService.findAll();
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
        try {
            Product actualProduct = productService.findById(id);
            actualProduct.setName(product.getName());
            actualProduct.setDescription(product.getDescription());
            actualProduct.setPrice(product.getPrice());
            actualProduct.setStock(product.getStock());
            productService.save(actualProduct);
        } catch (DataAccessException e) {
            response.put("message", "Data Base Exception!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Product has been updated successfully!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

    }
}
