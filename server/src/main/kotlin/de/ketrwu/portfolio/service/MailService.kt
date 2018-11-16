package de.ketrwu.portfolio.service

import de.ketrwu.portfolio.entity.ContactForm

/**
 * Service for building, queuing and sending emails
 * @author Kenneth Wußmann
 */
interface MailService {

    /**
     * Send a ContactForm mail
     */
    fun sendMail(contactForm: ContactForm)
}
