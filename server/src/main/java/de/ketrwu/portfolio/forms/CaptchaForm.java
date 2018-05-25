package de.ketrwu.portfolio.forms;

import de.ketrwu.portfolio.forms.validation.captcha.Captcha;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Captcha(message = "Die Captcha-Lösung war nicht korrekt.")
public class CaptchaForm extends Form {

    @NotEmpty
    private String captchaResponse;

    @NotEmpty
    private String captchaToken;

    private String captchaImage;

}
