package com.marcorh96.springboot.rest.ecommerce.app.models.document;

import java.io.Serializable;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Document(collection = "users")
public class User implements Serializable {

    @Id
    private String id;

    private Person person;

    @Indexed( unique = true)
    private String email;

    private String password;

    private Address address;

    private String role;

    @Field("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdAt;

    public User() {
    }

    public User(Person person, String email, String password, Address address, String role) {
        this.person = person;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
        this.createdAt = new Date();
    }

    private static final long serialVersionUID = 1L;
}
