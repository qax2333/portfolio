package de.ketrwu.portfolio.forms.validation.formtoken;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FormTokenValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FormToken {

    String message() default "FormToken invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
