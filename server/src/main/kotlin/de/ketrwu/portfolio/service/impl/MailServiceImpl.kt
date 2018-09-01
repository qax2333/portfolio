package de.ketrwu.portfolio.service.impl

import de.ketrwu.portfolio.Slf4j
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
import java.util.ArrayList

/**
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

    override fun sendMail(contactForm: ContactForm) {
        val ownerMail = buildEmail(contactForm, true)
        val senderMail = buildEmail(contactForm, false)
        sendMailTemplate(mailOwner, contactForm.getEmail(), "Neue Nachricht von " + contactForm.getEmail(), ownerMail)
        sendMailTemplate(contactForm.getEmail(), mailOwner, "Deine Anfrage übers Kontaktformular", senderMail)
    }

    private fun sendMailTemplate(to: String?, replyTo: String?, subject: String, content: String) {
        mailQueue.add({ mimeMessage ->
            val messageHelper = MimeMessageHelper(mimeMessage)
            messageHelper.setFrom(mailSender!!)
            messageHelper.setTo(to!!)
            messageHelper.setReplyTo(replyTo!!)
            messageHelper.setSubject(subject)
            messageHelper.setText(content, true)
        })
    }

    private fun buildEmail(contactForm: ContactForm, owner: Boolean): String {
        val context = Context()
        context.setVariable("email", contactForm)
        return if (owner) {
            templateEngine!!.process("mails/owner-notify", context)
        } else {
            templateEngine!!.process("mails/sender-notify", context)
        }
    }

    // every 10 minutes
    @Scheduled(fixedRate = 600000)
    private fun sendMailQueue() {
        if (mailQueue.size > 0) {
            log.info("Sending {} mails from queue", mailQueue.size)
            for (mimeMessagePreparator in mailQueue) {
                emailSender!!.send(mimeMessagePreparator)
            }
            mailQueue.clear()
        }
    }

    companion object {
        @Slf4j
        lateinit var log: Logger
    }

}
