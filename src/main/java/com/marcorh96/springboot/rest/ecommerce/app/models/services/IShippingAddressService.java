package com.marcorh96.springboot.rest.ecommerce.app.models.services;

import com.marcorh96.springboot.rest.ecommerce.app.models.document.ShippingAddress;

public interface IShippingAddressService{
    
    public ShippingAddress findById(String id);

    public ShippingAddress save(ShippingAddress shippingAddress);

    public void delete(String id);
}
