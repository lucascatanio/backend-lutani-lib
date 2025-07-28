package br.com.lutani.lutani_lib.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = AnoNaoFuturoValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AnoNaoFuturo {
    String message() default "O ano de publicação não pode ser no futuro.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}