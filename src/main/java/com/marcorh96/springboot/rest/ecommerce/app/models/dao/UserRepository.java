package com.marcorh96.springboot.rest.ecommerce.app.models.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcorh96.springboot.rest.ecommerce.app.models.document.User;

public interface UserRepository extends MongoRepository<User, String> {
    
    public Optional<User> findByEmail(String email);

}
