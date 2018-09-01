package de.ketrwu.portfolio.service

import de.ketrwu.portfolio.forms.ContactForm

/**
 * @author Kenneth Wußmann
 */
interface MailService {

    fun sendMail(contactForm: ContactForm)

}
