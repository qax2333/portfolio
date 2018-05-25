package de.ketrwu.portfolio.forms;

import de.ketrwu.portfolio.forms.validation.formtoken.FormToken;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@FormToken(message = "Dieses Formular kann nicht erneut abgesendet werden.")
public class Form {

    @NotEmpty
    private String formToken;

}
