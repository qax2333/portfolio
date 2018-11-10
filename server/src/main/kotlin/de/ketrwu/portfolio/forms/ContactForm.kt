package de.ketrwu.portfolio.forms

import javax.validation.constraints.AssertTrue
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

/**
 * @author Kenneth Wußmann
 */
class ContactForm : CaptchaForm() {

    @NotEmpty(message = "E-Mail Adresse ist ein Pflichtfeld.")
    @Email(message = "E-Mail Adresse ist nicht korrekt.")
    var email: String? = null

    @NotEmpty(message = "Nachricht ist ein Pflichtfeld")
    @Size(min = 10, message = "Nachricht ist zu kurz. Mindestens 10 Zeichen.")
    var message: String? = null

    @AssertTrue(message = "Dem Datenschutzhinweis muss zugestimmt werden.")
    var privacyAgreement = false
}