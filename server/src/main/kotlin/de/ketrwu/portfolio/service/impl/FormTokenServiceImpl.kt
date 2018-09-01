package de.ketrwu.portfolio.service.impl

import de.ketrwu.portfolio.forms.Form
import de.ketrwu.portfolio.service.FormTokenService
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author Kenneth Wußmann
 */
@Service
class FormTokenServiceImpl : FormTokenService {

    private val tokens = ArrayList<String>()

    override fun tokenize(form: Form) {
        form.setFormToken(UUID.randomUUID().toString())
        tokens.add(form.getFormToken())
    }

    override fun isFormTokenValid(form: Form): Boolean {
        return if (form.getFormToken() == null) {
            false
        } else tokens.contains(form.getFormToken())
    }

    override fun invalidateFormToken(form: Form) {
        if (isFormTokenValid(form)) {
            tokens.remove(form.getFormToken())
            form.setFormToken(null)
        }
    }

}
