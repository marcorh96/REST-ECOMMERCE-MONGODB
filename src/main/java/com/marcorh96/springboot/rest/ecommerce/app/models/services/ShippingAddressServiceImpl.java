package com.marcorh96.springboot.rest.ecommerce.app.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcorh96.springboot.rest.ecommerce.app.models.dao.ShippingAddressRepository;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.ShippingAddress;

@Service
public class ShippingAddressServiceImpl implements IShippingAddressService{

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;
    
    @Override
    public ShippingAddress findById(String id) {
        return shippingAddressRepository.findById(id).orElse(null);
    }

    @Override
    public ShippingAddress save(ShippingAddress shippingAddress) {
        return shippingAddressRepository.save(shippingAddress);
    }

    @Override
    public void delete(String id) {
        shippingAddressRepository.deleteById(id);
    }
    
}
