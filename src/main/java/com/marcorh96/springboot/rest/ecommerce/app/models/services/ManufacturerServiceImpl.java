package com.marcorh96.springboot.rest.ecommerce.app.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcorh96.springboot.rest.ecommerce.app.models.dao.ManufacturerRepository;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.Manufacturer;

@Service
public class ManufacturerServiceImpl implements IManufacturerService{

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Override
    public Manufacturer findById(String id) {
       return manufacturerRepository.findById(id).orElse(null);
    }

    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public void delete(String id) {
        manufacturerRepository.deleteById(id);
    }
    
}
