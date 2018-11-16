package de.ketrwu.portfolio.service

import de.ketrwu.portfolio.entity.Form

/**
 * Service to add valid tokens to forms, offer validation of form tokens and invalidation of tokens
 * @author Kenneth Wußmann
 */
interface FormTokenService {

    /**
     * Set a new token for the form
     */
    fun tokenize(form: Form)

    /**
     * Check if a form token is valid
     */
    fun isFormTokenValid(form: Form): Boolean

    /**
     * Remove a valid form token from a form and remove it from the token store
     */
    fun invalidateFormToken(form: Form)
}
