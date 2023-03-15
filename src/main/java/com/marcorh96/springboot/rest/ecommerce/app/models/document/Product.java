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
import java.util.List;

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
    private Category category;

    @NonNull
    private List<String> features;

    @NonNull
    private Double price;

    @NonNull
    private Integer stock;

    @NonNull
    private String color;

    @NonNull
    private Manufacturer manufacturer;

    private String photo;

    
    @Field(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdAt;

    @Field(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date updatedAt;

    public Product(String name, String description, Category category, List<String> features,Double price, Integer stock, String color, Manufacturer manufacturer) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.features = features;
        this.price = price;
        this.stock = stock;
        this.color = color;
        this.manufacturer = manufacturer;
        this.createdAt = new Date();

    }

    private static final long serialVersionUID = 1L;

}
