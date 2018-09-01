package de.ketrwu.portfolio.forms.validation

import de.ketrwu.portfolio.forms.CaptchaForm
import de.ketrwu.portfolio.service.CaptchaService
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Constraint(validatedBy = [CaptchaValidator::class])
@Retention(AnnotationRetention.RUNTIME)
annotation class Captcha(
    val message: String = "Captcha invalid",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class CaptchaValidator : ConstraintValidator<Captcha, CaptchaForm> {

    @Autowired
    private val captchaService: CaptchaService? = null

    override fun initialize(captcha: Captcha?) {}

    override fun isValid(captchaForm: CaptchaForm, cxt: ConstraintValidatorContext): Boolean {
        return captchaService!!.checkCaptcha(captchaForm)
    }
}