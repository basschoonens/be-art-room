package nl.novi.theartroom.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ArtworkTypeValidator implements ConstraintValidator<ValidArtworkType, String> {
    @Override
    public void initialize(ValidArtworkType constraintAnnotation) {
    }

    @Override
    public boolean isValid(String artworkType, ConstraintValidatorContext constraintValidatorContext) {
        return artworkType != null && (artworkType.equals("drawing") || artworkType.equals("painting"));
    }
}