package com.marcorh96.springboot.rest.ecommerce.app.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcorh96.springboot.rest.ecommerce.app.models.dao.AddressRepository;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.Address;

@Service
public class AddressServiceImpl implements IAddressService{

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address findById(String id) {
        return addressRepository.findById(id).orElse(null);
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public void delete(String id) {
        addressRepository.deleteById(id);
    }
    
}
