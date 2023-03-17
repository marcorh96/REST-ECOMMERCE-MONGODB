package com.marcorh96.springboot.rest.ecommerce.app.models.document;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Document(collection = "order_items")
public class OrderItem {

    @JsonIgnoreProperties(value = { "photo", "createdAt", "updatedAt"})
    private Product product;

    private Integer quantity;


    public OrderItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Double getPrice(){
        return this.quantity.doubleValue() * product.getPrice();
    }

}
