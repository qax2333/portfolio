package de.ketrwu.portfolio.service;

import de.ketrwu.portfolio.forms.Form;

public interface FormTokenService {

    void tokenize(Form form);

    boolean isFormTokenValid(Form form);

    void invalidateFormToken(Form form);

}
