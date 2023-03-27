package com.marcorh96.springboot.rest.ecommerce.app.validation;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

class AdultAgeValidator implements ConstraintValidator<AdultAge, Date> {

    private String pattern;

    @Override
    public void initialize(AdultAge adultAge) {
        pattern = adultAge.value();
    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext context) {
        if (date == null) {
            return true; 
        }
        LocalDate birthdate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        Period period = Period.between(birthdate, now);
        return period.getYears() >= 18;
    }
}