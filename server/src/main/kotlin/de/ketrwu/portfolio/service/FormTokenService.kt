package de.ketrwu.portfolio.service

import de.ketrwu.portfolio.forms.Form

/**
 * @author Kenneth Wußmann
 */
interface FormTokenService {

    fun tokenize(form: Form)

    fun isFormTokenValid(form: Form): Boolean

    fun invalidateFormToken(form: Form)

}
