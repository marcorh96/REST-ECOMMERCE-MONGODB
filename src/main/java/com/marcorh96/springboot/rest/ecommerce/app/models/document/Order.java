package com.marcorh96.springboot.rest.ecommerce.app.models.document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mongodb.lang.NonNull;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "orders")
public class Order implements Serializable {

    @Id
    private String id;

    @JsonIgnoreProperties(value = { "orders", "password", "photo", "enabled", "accountNonLocked", "username",
            "authorities", "credentialsNonExpired", "accountNonExpired" })
    @DBRef
    @NonNull
    private User user;

    @NonNull
    private List<OrderItem> items;

    @NonNull
    @DBRef
    private ShippingAddress shippingAddress;

    @Field(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdAt = new Date();

    @NonNull
    private String status;

    public Order() {
    }

    public Order(User user, List<OrderItem> items, String status, ShippingAddress shippingAddress) {
        this.user = user;
        this.items = items;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.createdAt = new Date();
    }

    public Double getTotal() {
        Double total = 0.0;
        for (OrderItem item : items) {
            total += item.getPrice();
        }
        return total;
    }
}
