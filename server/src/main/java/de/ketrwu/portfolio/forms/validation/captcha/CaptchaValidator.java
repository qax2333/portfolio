package de.ketrwu.portfolio.forms.validation.captcha;

import de.ketrwu.portfolio.forms.CaptchaForm;
import de.ketrwu.portfolio.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CaptchaValidator implements ConstraintValidator<Captcha, CaptchaForm> {

    @Autowired
    private CaptchaService captchaService;

    @Override
    public void initialize(Captcha captcha) {
    }

    @Override
    public boolean isValid(CaptchaForm captchaForm, ConstraintValidatorContext cxt) {
        return captchaService.checkCaptcha(captchaForm);
    }

}
