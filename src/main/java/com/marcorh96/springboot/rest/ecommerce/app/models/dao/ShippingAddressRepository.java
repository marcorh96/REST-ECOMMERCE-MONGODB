package com.marcorh96.springboot.rest.ecommerce.app.models.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcorh96.springboot.rest.ecommerce.app.models.document.ShippingAddress;

public interface ShippingAddressRepository extends MongoRepository<ShippingAddress, String>{
    
}
