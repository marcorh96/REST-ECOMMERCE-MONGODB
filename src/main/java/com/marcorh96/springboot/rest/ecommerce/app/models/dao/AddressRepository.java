package com.marcorh96.springboot.rest.ecommerce.app.models.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.Address;

public interface AddressRepository extends MongoRepository<Address, String>{
    
}
