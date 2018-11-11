package de.ketrwu.portfolio.service

import de.ketrwu.portfolio.forms.CaptchaForm
import java.io.IOException

/**
 * @author Kenneth Wußmann
 */
interface CaptchaService {

    /**
     * Create a BASE64 encoded captcha image
     */
    @Throws(IOException::class)
    fun createCaptcha(captchaForm: CaptchaForm): String

    /**
     * Validate a captcha response in a CaptchaForm
     */
    fun checkCaptcha(captchaForm: CaptchaForm): Boolean

    /**
     * Load captcha fonts randomly used for captcha creation
     */
    fun loadFonts()
}
