package de.ketrwu.portfolio.forms;

import de.ketrwu.portfolio.forms.validation.formtoken.FormToken;
import lombok.Data;

@Data
@FormToken(message = "Dieses Formular kann nicht erneut abgesendet werden.")
public class Form {

    private String formToken;

}
