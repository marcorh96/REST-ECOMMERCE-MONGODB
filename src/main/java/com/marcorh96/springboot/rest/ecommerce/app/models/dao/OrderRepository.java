package com.marcorh96.springboot.rest.ecommerce.app.models.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcorh96.springboot.rest.ecommerce.app.models.document.Order;


public interface OrderRepository extends MongoRepository<Order, String>{
    
    public void deleteOrdersByUserId(String userId);

    public List<Order> findOrdersByUserId(String userId);

}
