package com.marcorh96.springboot.rest.ecommerce.app.models.services;

import com.marcorh96.springboot.rest.ecommerce.app.models.document.Manufacturer;

public interface IManufacturerService {
    public Manufacturer findById(String id);

    public Manufacturer save(Manufacturer manufacturer);

    public void delete(String id);
}
