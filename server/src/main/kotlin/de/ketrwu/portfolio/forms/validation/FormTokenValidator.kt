package de.ketrwu.portfolio.forms.validation

import de.ketrwu.portfolio.forms.Form
import de.ketrwu.portfolio.service.FormTokenService
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

/**
 * Annotation to assign FormTokenValidator to a class
 * @author Kenneth Wußmann
 */
@Constraint(validatedBy = [FormTokenValidator::class])
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
annotation class FormToken(
    val message: String = "FormToken invalid",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

/**
 * ConstraintValidator to validate token of a form
 * @author Kenneth Wußmann
 */
class FormTokenValidator : ConstraintValidator<FormToken, Form> {

    @Autowired
    private val formTokenService: FormTokenService? = null

    override fun initialize(formToken: FormToken?) {}

    override fun isValid(form: Form, cxt: ConstraintValidatorContext): Boolean {
        return formTokenService?.isFormTokenValid(form) ?: false
    }
}