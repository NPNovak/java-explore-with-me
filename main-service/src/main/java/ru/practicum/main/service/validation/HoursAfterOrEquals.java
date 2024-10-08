package ru.practicum.main.service.validation;

import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidationHoursAfterOrEquals.class)
public @interface HoursAfterOrEquals {
    String message();

    int hours();

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
