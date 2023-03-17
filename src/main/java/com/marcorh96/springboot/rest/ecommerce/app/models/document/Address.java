package com.marcorh96.springboot.rest.ecommerce.app.models.document;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection ="addresses")
public class Address  implements Serializable{

    @Id
    private String id;

    private String street;

    private String city;

    private String state;

    private String country;

    private Integer zipcode;
    

    public Address(String street, String city, String state, String country, Integer zipcode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
    }



    private static final long serialVersionUID = 1L;

}
