package com.marcorh96.springboot.rest.ecommerce.app.models.services;

import java.util.List;

import com.marcorh96.springboot.rest.ecommerce.app.models.document.User;

public interface IUserService {
    public List<User> findAll();

    public User findById(String id);

    public User save(User user);

    public void delete(String id);

    public List<User> saveAll(List<User> users);

    public void deleteOrdersByUserId(String userId);

    
}
