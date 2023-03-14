package com.marcorh96.springboot.rest.ecommerce.app.models.services;

import java.util.List;

import com.marcorh96.springboot.rest.ecommerce.app.models.document.Order;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.OrderItem;

public interface IOrderService {
    public List<Order> findAll();

    public Order findById(String id);

    public Order save(Order order);

    public void delete(String id);

    public void updateStock(List<OrderItem> orderItems);

    public List<Order> findOrdersByUserId(String userId);

    public List<Order> saveAll(List<Order> orders);

}
