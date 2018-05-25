package de.ketrwu.portfolio.service;

import de.ketrwu.portfolio.forms.ContactForm;

public interface MailService {

    void sendMail(ContactForm contactForm);

}
