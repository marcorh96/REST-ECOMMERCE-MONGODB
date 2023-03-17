package com.marcorh96.springboot.rest.ecommerce.app.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcorh96.springboot.rest.ecommerce.app.models.dao.PersonRepository;
import com.marcorh96.springboot.rest.ecommerce.app.models.document.Person;

@Service
public class PersonServiceImpl implements IPersonService{

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person findById(String id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public void delete(String id) {
        personRepository.deleteById(id);
    }
    
}
