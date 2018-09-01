package de.ketrwu.portfolio.forms

import de.ketrwu.portfolio.forms.validation.captcha.Captcha
import javax.validation.constraints.NotEmpty

/**
 * @author Kenneth Wußmann
 */

@Captcha(message = "Die Captcha-Lösung war nicht korrekt.")
open class CaptchaForm : Form(formToken = formToken) {

    @NotEmpty
    val captchaResponse: String? = null

    @NotEmpty
    val captchaToken: String? = null

    val captchaImage: String? = null

    val formToken: String? = null


}
