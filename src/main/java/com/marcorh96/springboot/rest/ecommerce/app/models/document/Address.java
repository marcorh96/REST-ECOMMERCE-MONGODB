package com.marcorh96.springboot.rest.ecommerce.app.models.document;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Document(collection = "addresses")
public class Address implements Serializable {

    @Id
    private String id;

    @NotEmpty(message = "cannot be empty")
    private String street;

    @NotEmpty(message = "cannot be empty")
    private String city;

    @NotEmpty(message = "cannot be empty")
    private String state;

    @NotEmpty(message = "cannot be empty")
    private String country;

    @NotEmpty(message = "cannot be empty")
    @Pattern(regexp = "\\d{5}", message = "must contain 5 digit numbers")
    private String zipcode;

    public Address(String street, String city, String state, String country, String zipcode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
    }

    private static final long serialVersionUID = 1L;

}
