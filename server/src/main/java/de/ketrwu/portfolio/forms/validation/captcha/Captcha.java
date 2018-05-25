package de.ketrwu.portfolio.forms.validation.captcha;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CaptchaValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Captcha {

    String message() default "Captcha invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}