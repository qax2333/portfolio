package de.ketrwu.portfolio.service.impl;

import de.ketrwu.portfolio.forms.ContactForm;
import de.ketrwu.portfolio.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    private List<MimeMessagePreparator> mailQueue = new ArrayList<>();

    @Override
    public void sendMail(ContactForm contactForm) {
        String ownerMail = buildEmail(contactForm, true);
        String senderMail = buildEmail(contactForm, false);
        sendMailTemplate(mailOwner, contactForm.getEmail(),"Neue Nachricht von " + contactForm.getEmail(), ownerMail);
        sendMailTemplate(contactForm.getEmail(), mailOwner,"Deine Anfrage übers Kontaktformular", senderMail);
    }

    private void sendMailTemplate(String to, String replyTo, String subject, String content) {
        mailQueue.add(mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(mailSender);
            messageHelper.setTo(to);
            messageHelper.setReplyTo(replyTo);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
        });
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

    // every 10 minutes
    @Scheduled(fixedRate = 600000)
    private void sendMailQueue() {
        if (mailQueue.size() > 0) {
            log.info("Sending {} mails from queue", mailQueue.size());
            for (MimeMessagePreparator mimeMessagePreparator : mailQueue) {
                emailSender.send(mimeMessagePreparator);
            }
            mailQueue.clear();
        }
    }

}
