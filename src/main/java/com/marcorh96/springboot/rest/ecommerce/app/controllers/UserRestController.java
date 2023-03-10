package com.marcorh96.springboot.rest.ecommerce.app.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marcorh96.springboot.rest.ecommerce.app.models.document.User;
import com.marcorh96.springboot.rest.ecommerce.app.models.services.IUserService;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private IUserService userService;

    @GetMapping("/users")
    public List<User> showUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User showUser(@PathVariable String id) {
        return userService.findById(id);
    }

    @PostMapping("/users")
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
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        try {
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
            response.put("mensaje", "Data Base Exception!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "User has been updated successfully!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

}
