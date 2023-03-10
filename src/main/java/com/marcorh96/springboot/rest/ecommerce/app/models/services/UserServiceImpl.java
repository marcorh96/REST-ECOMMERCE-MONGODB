package com.marcorh96.springboot.rest.ecommerce.app.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcorh96.springboot.rest.ecommerce.app.models.dao.UserRepository;
import com.marcorh96.springboot.rest.ecommerce.app.models.dao.OrderRepository;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.User;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;
   

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> saveAll(List<User> users){
        return userRepository.saveAll(users);
    }
    
    @Override
    public void deleteOrdersByUserId(String userId){
        orderRepository.deleteOrdersByUserId(userId);
    }

}
