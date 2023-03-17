package com.marcorh96.springboot.rest.ecommerce.app.models.document;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "shipping_address")
public class ShippingAddress {

    @Id
    private String id;

    private String street;

    private String city;

    private String state;

    private String country;

    private Integer zipcode;
    

    public ShippingAddress(String street, String city, String state, String country, Integer zipcode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
    }

}
