package com.vili.mrmentoria.api.validation.constraints;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.vili.mrmentoria.api.validation.MentoriaValidator;

@Documented
@Retention(RUNTIME)
@Target(TYPE_USE)
@Constraint(validatedBy = MentoriaValidator.class)
public @interface ValidMentoria {

	String message() default "Mentoria validation error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
