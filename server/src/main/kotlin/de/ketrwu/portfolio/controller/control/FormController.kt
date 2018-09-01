package de.ketrwu.portfolio.controller.control

import de.ketrwu.portfolio.forms.CaptchaForm
import de.ketrwu.portfolio.forms.Form
import de.ketrwu.portfolio.forms.validation.FormToken
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

@Controller
open abstract class FormController<T : Form> {

    @Autowired
    private val captchaService: CaptchaService? = null

    @Autowired
    private val formTokenService: FormTokenService? = null

    abstract val template: String

    @GetMapping
    @Throws(IllegalAccessException::class, IOException::class, InstantiationException::class)
    fun getForm(@ModelAttribute("form") form: T, model: MutableMap<String, Any>): String {
        model["form"] = resetForm(form)
        return template
    }

    @PostMapping
    @Throws(IllegalAccessException::class, InstantiationException::class, IOException::class)
    fun submitForm(@ModelAttribute("form") @Validated _form: T, bindingResult: BindingResult, model: MutableMap<String, Any>): String {
        var form = _form
        if (!bindingResult.hasErrors()) {
            onSuccess(form)
            formTokenService!!.invalidateFormToken(form)
            form = resetForm(form)
        } else if (form is CaptchaForm) {
            captchaService!!.createCaptcha(form as CaptchaForm)
        }

        if (bindingResult.hasErrors()) {
            onError(form, bindingResult)
        }

        val errors = ArrayList<String>()
        for (error in bindingResult.allErrors) {
            if (error.code == FormToken::class.java.simpleName) {
                onResubmit(form)
                errors.clear()
                errors.add(error.defaultMessage!!)
                formTokenService!!.invalidateFormToken(form)
                form = resetForm(form)
                break
            }
            errors.add(error.defaultMessage!!)
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
        val newform = form.javaClass.newInstance() as T
        formTokenService!!.tokenize(newform)
        if (newform is CaptchaForm) {
            captchaService!!.createCaptcha(newform as CaptchaForm)
        }
        return newform
    }

    open fun onSuccess(form: T) {}

    open fun onResubmit(form: T) {}

    open fun onError(form: T, bindingResult: BindingResult) {}
}