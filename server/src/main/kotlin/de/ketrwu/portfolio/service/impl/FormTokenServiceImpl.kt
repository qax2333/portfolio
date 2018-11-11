package de.ketrwu.portfolio.service.impl

import de.ketrwu.portfolio.forms.Form
import de.ketrwu.portfolio.service.FormTokenService
import org.springframework.stereotype.Service
import java.util.UUID

/**
 * Implementation of the FormTokenService
 * @author Kenneth Wußmann
 */
@Service
class FormTokenServiceImpl : FormTokenService {

    private val tokens = ArrayList<String>()

    /**
     * {@inheritDoc}
     */
    override fun tokenize(form: Form) {
        UUID.randomUUID().toString().let {
            form.formToken = it
            tokens.add(it)
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun isFormTokenValid(form: Form): Boolean {
        return tokens.contains(form.formToken)
    }

    /**
     * {@inheritDoc}
     */
    override fun invalidateFormToken(form: Form) {
        if (isFormTokenValid(form)) {
            tokens.remove(form.formToken)
            form.formToken = null
        }
    }
}
