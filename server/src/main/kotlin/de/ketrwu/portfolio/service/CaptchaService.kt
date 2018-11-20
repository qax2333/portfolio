package de.ketrwu.portfolio.service

import de.ketrwu.portfolio.entity.CaptchaForm
import de.ketrwu.portfolio.entity.api.CaptchaReloadRequest
import de.ketrwu.portfolio.entity.api.CaptchaReloadResponse
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

    /**
     * Invalidate old captcha and generate a new BASE64 image
     */
    fun reloadCaptcha(captchaReloadRequest: CaptchaReloadRequest): CaptchaReloadResponse?
}
