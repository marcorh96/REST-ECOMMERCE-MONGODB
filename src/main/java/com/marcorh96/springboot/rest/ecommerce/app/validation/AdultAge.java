package com.marcorh96.springboot.rest.ecommerce.app.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;



@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { AdultAgeValidator.class })
@Documented
public @interface AdultAge {

    String message() default "must be at least 18 years old to register";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String value() default "yyyy-MM-dd";
}