package de.ketrwu.portfolio.service

import de.ketrwu.portfolio.forms.CaptchaForm
import java.io.IOException

/**
 * @author Kenneth Wußmann
 */
interface CaptchaService {

    @Throws(IOException::class)
    fun createCaptcha(captchaForm: CaptchaForm): String

    fun checkCaptcha(captchaForm: CaptchaForm): Boolean

    fun loadFonts()

}
