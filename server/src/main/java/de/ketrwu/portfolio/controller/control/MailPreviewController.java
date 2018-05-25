package de.ketrwu.portfolio.controller.control;

import de.ketrwu.portfolio.forms.ContactForm;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
@ConditionalOnExpression("${app.debug:false}")
public class MailPreviewController {

    @GetMapping("/mail/owner")
    public String getMailPreview(Map<String, Object> model) {
        model.put("email", createFakeMail());
        return "mails/owner-notify.html";
    }

    @GetMapping("/mail/sender")
    public String getMailSenderPreview(Map<String, Object> model) {
        model.put("email", createFakeMail());
        return "mails/sender-notify.html";
    }

    private ContactForm createFakeMail() {
        ContactForm email = new ContactForm();
        email.setEmail("preview@mail.local");
        email.setMessage("Lorem \nipsum dolor sit amet, consetetur \n <b>Escaping?</b>\nsadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");
        email.setMessage(email.getMessage() + email.getMessage());
        return email;
    }

}
