package com.marcorh96.springboot.rest.ecommerce.app.models.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marcorh96.springboot.rest.ecommerce.app.validation.AdultAge;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Document(collection = "persons")
public class Person {

    @Id
    private String id;

    @NotEmpty(message = "cannot be empty")
    private String firstname;

    @NotEmpty(message = "cannot be empty")
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Field("birth_date")
    @NotNull(message = "cannot be empty")
    @AdultAge
    private Date birthDate;

    @NotEmpty(message = "cannot be empty")
    @Pattern(regexp = "\\d{10}", message = "must contain 10 digit numbers")
    private String phone;

    public Person(String firstname, String lastName, Date birthDate, String phone) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phone = phone;
    }

}
