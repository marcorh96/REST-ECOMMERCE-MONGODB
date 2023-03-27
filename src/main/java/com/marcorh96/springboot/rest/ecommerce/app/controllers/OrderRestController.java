package com.marcorh96.springboot.rest.ecommerce.app.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.marcorh96.springboot.rest.ecommerce.app.models.document.Order;
import com.marcorh96.springboot.rest.ecommerce.app.models.services.IOrderService;
import com.marcorh96.springboot.rest.ecommerce.app.models.services.IProductService;
import com.marcorh96.springboot.rest.ecommerce.app.models.services.IShippingAddressService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = { "http://127.0.0.1:5173/", "*" })
@RestController
@RequestMapping("/api")
public class OrderRestController {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IShippingAddressService shippingAddressService;

    @GetMapping("/orders")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Order> showOrders() {
        return orderService.findAll();
    }

    @GetMapping("/orders/user/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public List<Order> showOrdersById(@PathVariable String id) {
        return orderService.findOrdersByUserId(id);
    }

    @GetMapping("/orders/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public Order showOrder(@PathVariable String id) {
        return orderService.findById(id);
    }

    @PostMapping("/orders")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        Order orderNew = null;
        Map<String, Object> response = new HashMap<>();
        try {
            shippingAddressService.save(order.getShippingAddress());
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteOrder(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        Order order = null;
        try {
            order = orderService.findById(id);
            shippingAddressService.delete(order.getShippingAddress().getId());
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Order order) {

        Map<String, Object> response = new HashMap<>();
        try {
            Order actualOrder = orderService.findById(id);
            shippingAddressService.save(order.getShippingAddress());
            actualOrder.setStatus(order.getStatus());
            actualOrder.setShippingAddress(order.getShippingAddress());
            orderService.save(actualOrder);
        } catch (DataAccessException e) {
            response.put("message", "Data Base Exception!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "Order status has been updated successfully!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
