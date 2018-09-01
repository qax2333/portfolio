package de.ketrwu.portfolio.service.impl

import de.ketrwu.portfolio.forms.Form
import de.ketrwu.portfolio.service.FormTokenService
import org.springframework.stereotype.Service
import java.util.UUID

/**
 * @author Kenneth Wußmann
 */
@Service
class FormTokenServiceImpl : FormTokenService {

    private val tokens = ArrayList<String>()

    override fun tokenize(form: Form) {
        form.formToken = UUID.randomUUID().toString()
        tokens.add(form.formToken!!)
    }

    override fun isFormTokenValid(form: Form): Boolean {
        return if (form.formToken != null) tokens.contains(form.formToken!!) else false
    }

    override fun invalidateFormToken(form: Form) {
        if (isFormTokenValid(form)) {
            tokens.remove(form.formToken)
            form.formToken = null
        }
    }
}
