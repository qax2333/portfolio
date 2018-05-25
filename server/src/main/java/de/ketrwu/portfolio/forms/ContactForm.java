package de.ketrwu.portfolio.forms;


import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ContactForm extends CaptchaForm {

    @NotEmpty(message = "E-Mail Adresse ist ein Pflichtfeld.")
    @Email(message = "E-Mail Adresse ist nicht korrekt.")
    private String email;

    @NotEmpty(message = "Nachricht ist ein Pflichtfeld")
    @Size(min = 10, message = "Nachricht ist zu kurz. Mindestens 10 Zeichen.")
    private String message;

    @AssertTrue(message = "Dem Datenschutzhinweis muss zugestimmt werden.")
    private boolean privacyAgreement = false;

}
