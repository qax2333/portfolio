package de.ketrwu.portfolio.service.impl;

import de.ketrwu.portfolio.forms.ContactForm;
import de.ketrwu.portfolio.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class MailServiceImpl implements MailService {

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    public TemplateEngine templateEngine;

    @Value("${app.mail.sender}")
    private String mailSender;

    @Value("${app.mail.owner}")
    private String mailOwner;

    @Override
    public void sendMail(ContactForm contactForm) {
        String ownerMail = buildEmail(contactForm, true);
        String senderMail = buildEmail(contactForm, false);
        sendMailTemplate(mailOwner, contactForm.getEmail(),"Neue Nachricht von " + contactForm.getEmail(), ownerMail);
        sendMailTemplate(contactForm.getEmail(), mailOwner,"Deine Anfrage übers Kontaktformular", senderMail);
    }

    private void sendMailTemplate(String to, String replyTo, String subject, String content) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(mailSender);
            messageHelper.setTo(to);
            messageHelper.setReplyTo(replyTo);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
        };
        emailSender.send(messagePreparator);
    }

    private String buildEmail(ContactForm contactForm, boolean owner) {
        Context context = new Context();
        context.setVariable("email", contactForm);
        if (owner) {
            return templateEngine.process("mails/owner-notify", context);
        } else {
            return templateEngine.process("mails/sender-notify", context);
        }
    }

}
