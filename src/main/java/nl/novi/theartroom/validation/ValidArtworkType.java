package nl.novi.theartroom.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ArtworkTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidArtworkType {
    String message() default "Invalid artwork type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}