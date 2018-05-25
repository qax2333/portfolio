package de.ketrwu.portfolio.service;

import de.ketrwu.portfolio.forms.CaptchaForm;

import java.io.IOException;

public interface CaptchaService {

    String createCaptcha(CaptchaForm captchaForm) throws IOException;

    boolean checkCaptcha(CaptchaForm captchaForm);

    void loadFonts();

}
