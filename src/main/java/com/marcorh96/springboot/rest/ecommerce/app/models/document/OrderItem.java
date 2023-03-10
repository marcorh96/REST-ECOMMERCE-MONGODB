package com.marcorh96.springboot.rest.ecommerce.app.models.document;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "order_items")
public class OrderItem {


    private Product product;

    private Integer quantity;

    private ShippingAddress shippingAddress;

    public OrderItem(Product product, Integer quantity, ShippingAddress shippingAddress) {
        this.product = product;
        this.quantity = quantity;
        this.shippingAddress = shippingAddress;
    }

    public Double getPrice(){
        return this.quantity.doubleValue() * product.getPrice();
    }

}
