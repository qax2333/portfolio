package de.ketrwu.portfolio.controller.content

import de.ketrwu.portfolio.controller.control.FormController
import de.ketrwu.portfolio.forms.ContactForm
import de.ketrwu.portfolio.service.MailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 * @author Kenneth Wußmann
 */
@Controller
@RequestMapping("/contact")
class ContactController : FormController<ContactForm>() {

    @Autowired
    private val mailService: MailService? = null

    override fun getTemplate(): String {
        return "forms/contact"
    }

    override fun onSuccess(form: ContactForm) {
        mailService!!.sendMail(form)
    }

}
