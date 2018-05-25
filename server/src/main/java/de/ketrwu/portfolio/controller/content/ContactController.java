package de.ketrwu.portfolio.controller.content;

import de.ketrwu.portfolio.controller.control.FormController;
import de.ketrwu.portfolio.forms.ContactForm;
import de.ketrwu.portfolio.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contact")
public class ContactController extends FormController<ContactForm> {

    @Autowired
    private MailService mailService;

    @Override
    public String getTemplate() {
        return "forms/contact";
    }

    @Override
    public void onSuccess(ContactForm form) {
        mailService.sendMail(form);
    }
}
