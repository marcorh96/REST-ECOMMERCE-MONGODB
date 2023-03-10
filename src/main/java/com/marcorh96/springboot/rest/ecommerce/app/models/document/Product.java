package com.marcorh96.springboot.rest.ecommerce.app.models.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import java.io.Serializable;

import java.util.Date;

@Data
@Document(collection = "products")
public class Product implements Serializable {

    @Id
    private String id;

    private String name;

    private String description;

    private Double price;

    private Integer stock;

    
    @Field("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdAt;

    public Product(String name, String description, Double price, Integer stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.createdAt = new Date();

    }

    private static final long serialVersionUID = 1L;

}
