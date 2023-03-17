package com.marcorh96.springboot.rest.ecommerce.app.models.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marcorh96.springboot.rest.ecommerce.app.models.document.Manufacturer;

public interface ManufacturerRepository extends MongoRepository<Manufacturer, String>{
    
}
