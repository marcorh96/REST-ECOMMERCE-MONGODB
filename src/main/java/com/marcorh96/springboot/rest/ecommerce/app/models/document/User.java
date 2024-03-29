package com.marcorh96.springboot.rest.ecommerce.app.models.document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.lang.NonNull;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Document(collection = "users")
public class User implements Serializable, UserDetails {

    @Id
    private String id;

    @NotNull(message = "cannot be empty")
    @Valid
    @DBRef
    private Person person;

    @NotEmpty(message = "cannot be empty")
    @Email(message = "format is invalid")
    @Indexed(unique = true)
    private String email;

    @NotNull(message = "cannot be empty")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,}$", message = "must have at least one uppercase letter, one number, and be at least 8 characters long")
    private String password;

    @NotNull(message = "cannot be empty")
    @Valid
    @DBRef
    private Address address;

    @NonNull
    private Role role;


    @Field(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createdAt;
    
    private String photo;

    public User() {
    }

    public User(Person person, String email, String password, Address address, Role role) {
        this.person = person;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
        this.createdAt = new Date();
    }


    private static final long serialVersionUID = 1L;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        GrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        return authorities;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
