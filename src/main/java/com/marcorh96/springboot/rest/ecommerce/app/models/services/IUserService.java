package com.marcorh96.springboot.rest.ecommerce.app.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.marcorh96.springboot.rest.ecommerce.app.models.auth.UserLoginDTO;
import com.marcorh96.springboot.rest.ecommerce.app.models.auth.UserResponseDTO;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.User;

public interface IUserService {
    public List<User> findAll();

    public User findById(String id);

    public User save(User user);

    public User update(User user);

    public User updatePassword(User user);

    public void delete(String id);

    public List<User> saveAll(List<User> users);

    public void deleteOrdersByUserId(String userId);

    public UserResponseDTO login(UserLoginDTO user);

    public Page<User> findAll(Pageable pageable);

    public Boolean existsByEmail(String email);

    
}
