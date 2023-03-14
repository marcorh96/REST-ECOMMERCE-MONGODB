package com.marcorh96.springboot.rest.ecommerce.app.models.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mongodb.lang.NonNull;

import lombok.Data;
import java.io.Serializable;

import java.util.Date;

@Data
@Document(collection = "products")
public class Product implements Serializable {

    @Id
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    @NonNull
    private Double price;

    @NonNull
    private Integer stock;

    private String photo;

    
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
