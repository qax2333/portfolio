package de.ketrwu.portfolio.service.impl

import de.ketrwu.portfolio.Slf4j
import de.ketrwu.portfolio.exception.MailTemplateException
import de.ketrwu.portfolio.forms.ContactForm
import de.ketrwu.portfolio.service.MailService
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

/**
 * Implementation of a MailService to build mail templates and send them using a queuing
 * @author Kenneth Wußmann
 */
@Service
class MailServiceImpl : MailService {

    @Autowired
    var emailSender: JavaMailSender? = null

    @Autowired
    var templateEngine: TemplateEngine? = null

    @Value("\${app.mail.sender}")
    private val mailSender: String? = null

    @Value("\${app.mail.owner}")
    private val mailOwner: String? = null

    private val mailQueue = ArrayList<MimeMessagePreparator>()

    /**
     * {@inheritDoc}
     */
    override fun sendMail(contactForm: ContactForm) {
        try {
            val ownerMail = buildEmail(contactForm, true)
            val senderMail = buildEmail(contactForm, false)
            sendMailTemplate(mailOwner, contactForm.email, "Neue Nachricht von " + contactForm.email, ownerMail)
            sendMailTemplate(contactForm.email, mailOwner, "Deine Anfrage übers Kontaktformular", senderMail)
        } catch (e: MailTemplateException) {
            log.error(e.message, e)
        }
    }

    private fun sendMailTemplate(to: String?, replyTo: String?, subject: String, content: String) {
        mailQueue.add(
            MimeMessagePreparator { mimeMessage ->
                val messageHelper = MimeMessageHelper(mimeMessage)
                mailSender?.let { messageHelper.setFrom(it) }
                to?.let { messageHelper.setTo(it) }
                replyTo?.let { messageHelper.setReplyTo(it) }
                messageHelper.setSubject(subject)
                messageHelper.setText(content, true)
            }
        )
    }

    @Throws(MailTemplateException::class)
    private fun buildEmail(contactForm: ContactForm, owner: Boolean): String {
        val context = Context()
        context.setVariable("email", contactForm)
        return if (owner) {
            templateEngine?.process("mails/owner-notify", context)
        } else {
            templateEngine?.process("mails/sender-notify", context)
        } ?: throw MailTemplateException("Failed to render template")
    }

    // every 10 minutes
    @Scheduled(fixedRate = 600000)
    private fun sendMailQueue() {
        if (mailQueue.size > 0) {
            log.info("Sending {} mails from queue", mailQueue.size)
            for (mimeMessagePreparator in mailQueue) {
                emailSender?.send(mimeMessagePreparator)
            }
            mailQueue.clear()
        }
    }

    companion object {
        @Slf4j
        lateinit var log: Logger
    }
}
