package br.com.lutani.lutani_lib.validation;

import java.time.Year;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AnoNaoFuturoValidator implements ConstraintValidator<AnoNaoFuturo, Integer> {

    @Override
    public boolean isValid(Integer ano, ConstraintValidatorContext context) {
        if (ano == null) {
            return true;
        }
        return ano <= Year.now().getValue();
    }
}