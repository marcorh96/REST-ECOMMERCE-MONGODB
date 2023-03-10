package com.marcorh96.springboot.rest.ecommerce.app.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.marcorh96.springboot.rest.ecommerce.app.models.dao.OrderRepository;
import com.marcorh96.springboot.rest.ecommerce.app.models.dao.ProductRepository;

import com.marcorh96.springboot.rest.ecommerce.app.models.document.Order;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.OrderItem;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.Product;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(String id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void delete(String id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void updateStock(List<OrderItem> orderItems) {
        for (OrderItem item : orderItems) {
            Product product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }
    }

    @Override
    public List<Order> saveAll(List<Order> orders) {
        return orderRepository.saveAll(orders);
    }

   

    


}
