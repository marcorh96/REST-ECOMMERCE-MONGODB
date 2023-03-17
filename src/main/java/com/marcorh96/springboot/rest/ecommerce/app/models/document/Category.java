package com.marcorh96.springboot.rest.ecommerce.app.models.document;

import org.springframework.data.annotation.Id;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Category {

    @Id
    private String id;
    
    private String name;

    private String condition;

    public Category(String name, String condition) {
        this.name = name;
        this.condition = condition;
    }

    
}
