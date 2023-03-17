package com.marcorh96.springboot.rest.ecommerce.app.models.document;

import org.springframework.data.annotation.Id;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Manufacturer {


    @Id
    private String id;
    private String brand;
    private String model;
    private String manufacturer;
    private String distributor;

    public Manufacturer(String brand, String model, String manufacturer, String distributor) {
        this.brand = brand;
        this.model = model;
        this.manufacturer = manufacturer;
        this.distributor = distributor;
    }

    
}
