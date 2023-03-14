package com.marcorh96.springboot.rest.ecommerce.app.models.document;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Document(collection = "persons")
public class Person {


    private String firstname;

    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Field("birth_date")
    private Date birthDate;

    private String phone;


    public Person(String firstname, String lastName, Date birthDate, String phone) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phone = phone;
    }

}
