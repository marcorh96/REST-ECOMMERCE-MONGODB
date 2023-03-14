package com.marcorh96.springboot.rest.ecommerce.app.models.auth;

import com.marcorh96.springboot.rest.ecommerce.app.models.document.Role;

import lombok.Data;

@Data
public class UserResponseDTO {

    private String id;

    private String email;

    private String password;

    private Role role;

    private String token;
    
}
