package de.ketrwu.portfolio.entity

import de.ketrwu.portfolio.entity.validation.Captcha
import javax.validation.constraints.NotEmpty

/**
 * @author Kenneth Wußmann
 */

@Captcha(message = "Die Captcha-Lösung war nicht korrekt.")
open class CaptchaForm : Form() {

    @NotEmpty
    var captchaResponse: String? = null

    @NotEmpty
    var captchaToken: String? = null

    var captchaImage: String? = null
}
