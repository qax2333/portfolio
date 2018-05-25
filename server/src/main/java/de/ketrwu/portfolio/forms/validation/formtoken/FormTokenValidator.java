package de.ketrwu.portfolio.forms.validation.formtoken;

import de.ketrwu.portfolio.forms.Form;
import de.ketrwu.portfolio.service.FormTokenService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FormTokenValidator implements ConstraintValidator<FormToken, Form> {

    @Autowired
    private FormTokenService formTokenService;

    @Override
    public void initialize(FormToken formToken) {
    }

    @Override
    public boolean isValid(Form form, ConstraintValidatorContext cxt) {
        return formTokenService.isFormTokenValid(form);
    }

}
