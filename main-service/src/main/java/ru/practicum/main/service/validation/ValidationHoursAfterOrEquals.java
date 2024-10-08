package ru.practicum.main.service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class ValidationHoursAfterOrEquals implements ConstraintValidator<HoursAfterOrEquals, LocalDateTime> {

    private int hours;

    @Override
    public void initialize(HoursAfterOrEquals constraintAnnotation) {
        hours = constraintAnnotation.hours();
    }

    @Override
    public boolean isValid(LocalDateTime date, ConstraintValidatorContext context) {
        if (date == null) {
            return true;
        }

        return !LocalDateTime.now().plusHours(hours).isAfter(date);
    }
}
