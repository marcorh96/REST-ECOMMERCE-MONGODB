package com.marcorh96.springboot.rest.ecommerce.app.models.services;

import com.marcorh96.springboot.rest.ecommerce.app.models.document.Address;

public interface IAddressService {
    public Address findById(String id);

    public Address save(Address address);

    public void delete(String id);
}
