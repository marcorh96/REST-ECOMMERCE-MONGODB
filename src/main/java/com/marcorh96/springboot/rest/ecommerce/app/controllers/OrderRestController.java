package com.marcorh96.springboot.rest.ecommerce.app.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.marcorh96.springboot.rest.ecommerce.app.models.document.Order;
import com.marcorh96.springboot.rest.ecommerce.app.models.services.IOrderService;
import com.marcorh96.springboot.rest.ecommerce.app.models.services.IProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class OrderRestController {

    @Autowired
    public IOrderService orderService;

    @Autowired
    public IProductService productService;

    @GetMapping("/orders")
    public List<Order> showOrders() {
        return orderService.findAll();
    }

    @GetMapping("/orders/{id}")
    public Order showOrder(@PathVariable String id) {
        return orderService.findById(id);
    }

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        Order orderNew = null;
        Map<String, Object> response = new HashMap<>();
        try {
            orderNew = orderService.save(order);
            orderService.updateStock(orderNew.getItems());
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar el insert en la Base de datos!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Order has been created successfully");
        response.put("order", orderNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        try {

            orderService.delete(id);

        } catch (DataAccessException e) {
            response.put("message", "Data Base Exception!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Order has been deleted successfully!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Order order) {

        Map<String, Object> response = new HashMap<>();
        try {
            Order actualOrder = orderService.findById(id);
            actualOrder.setStatus(order.getStatus());
        } catch (DataAccessException e) {
            response.put("message", "Data Base Exception!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Order status has been updated successfully!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
