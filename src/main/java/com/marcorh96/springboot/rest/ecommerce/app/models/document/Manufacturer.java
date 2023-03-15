package com.marcorh96.springboot.rest.ecommerce.app.models.document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Manufacturer {
    private String brand;
    private String model;
    private String manufacturer;
    private String distributor;
}
