package com.marcorh96.springboot.rest.ecommerce.app.models.document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {
    
    private String name;

    private String condition;
}
