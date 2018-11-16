package de.ketrwu.portfolio.controller.control

import de.ketrwu.portfolio.entity.CaptchaForm
import de.ketrwu.portfolio.entity.Form
import de.ketrwu.portfolio.entity.validation.FormToken
import de.ketrwu.portfolio.service.CaptchaService
import de.ketrwu.portfolio.service.FormTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.io.IOException

/**
 * Abstract controller to handle form submission and validation
 * @author Kenneth Wußmann
 */
@Controller
abstract class FormController<T : Form> {

    @Autowired
    private val captchaService: CaptchaService? = null

    @Autowired
    private val formTokenService: FormTokenService? = null

    abstract val template: String

    /**
     * GetMapping for handling the loading of a form
     */
    @GetMapping
    @Throws(IllegalAccessException::class, IOException::class, InstantiationException::class)
    fun getForm(@ModelAttribute("form") form: T, model: MutableMap<String, Any>): String {
        model["form"] = resetForm(form)
        return template
    }

    /**
     * PostMapping for handling form submission and validation
     */
    @PostMapping
    @Throws(IllegalAccessException::class, InstantiationException::class, IOException::class)
    fun submitForm(@ModelAttribute("form") @Validated _form: T, bindingResult: BindingResult, model: MutableMap<String, Any>): String {
        var form = _form
        if (!bindingResult.hasErrors()) {
            onSuccess(form)
            formTokenService?.invalidateFormToken(form)
            form = resetForm(form)
        } else if (form is CaptchaForm) {
            captchaService?.createCaptcha(form)
        }

        if (bindingResult.hasErrors()) {
            onError(form, bindingResult)
        }

        val errors = ArrayList<String>()
        for (error in bindingResult.allErrors) {
            if (error.code == FormToken::class.java.simpleName) {
                onResubmit(form)
                errors.clear()
                error.defaultMessage?.let(errors::add)
                formTokenService?.invalidateFormToken(form)
                form = resetForm(form)
                break
            }
            error.defaultMessage?.let(errors::add)
        }

        errors.reverse()

        model["success"] = !bindingResult.hasErrors()
        model["error"] = bindingResult.hasErrors()
        model["errors"] = errors
        model["form"] = form

        return template
    }

    @Throws(IOException::class, IllegalAccessException::class, InstantiationException::class)
    private fun resetForm(form: T): T {
        return form.javaClass.newInstance().let {
            formTokenService?.tokenize(it)
            if (it is CaptchaForm) {
                captchaService?.createCaptcha(it)
            }
            it
        }
    }

    /**
     * Everything was fine, tell the controller if needed
     */
    open fun onSuccess(form: T) {}

    /**
     * Someone resubmitted a form (Page-Refresh), tell the controller if needed
     */
    open fun onResubmit(form: T) {}

    /**
     * A validation error occurred, tell the controller if needed
     */
    open fun onError(form: T, bindingResult: BindingResult) {}
}