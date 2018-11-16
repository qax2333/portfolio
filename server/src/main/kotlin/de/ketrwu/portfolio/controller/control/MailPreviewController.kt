package de.ketrwu.portfolio.controller.control

import de.ketrwu.portfolio.entity.ContactForm
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

/**
 * Debug controller to have a preview of a the rendered mail template
 * @author Kenneth Wußmann
 */
@Controller
@ConditionalOnExpression("\${app.debug:false}")
class MailPreviewController {

    /**
     * Get the rendered mail template sent to the owner
     */
    @GetMapping("/mail/owner")
    fun getMailPreview(model: MutableMap<String, Any>): String {
        model["email"] = createFakeMail()
        return "mails/owner-notify.html"
    }

    /**
     * Get the rendered mail template sent to the sender
     */
    @GetMapping("/mail/sender")
    fun getMailSenderPreview(model: MutableMap<String, Any>): String {
        model["email"] = createFakeMail()
        return "mails/sender-notify.html"
    }

    private fun createFakeMail(): ContactForm {
        val email = ContactForm()
        email.email = "preview@mail.local"
        email.message = """
            Lorem ipsum dolor sit amet, consetetur <b>Escaping?</b>
            sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam
            voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea
            takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr,
            sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.
            At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata
            sanctus est Lorem ipsum dolor sit amet.
        """.trimIndent()
        email.message += email.message
        return email
    }
}
