package com.marcorh96.springboot.rest.ecommerce.app.models.services;

import com.marcorh96.springboot.rest.ecommerce.app.models.document.Person;

public interface IPersonService {
    public Person findById(String id);

    public Person save(Person person);

    public void delete(String id);
}
